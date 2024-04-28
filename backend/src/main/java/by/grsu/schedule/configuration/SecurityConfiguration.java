package by.grsu.schedule.configuration;

import by.grsu.schedule.configuration.properties.CorsConfigurationProperties;
import by.grsu.schedule.configuration.properties.JwtConverterProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private final KeycloakLogoutHandler keycloakLogoutHandler;
    private final JwtConverter jwtConverter;
    private final CorsConfigurationProperties corsConfigurationProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(httpSecurityCorsConfigurer ->
                        httpSecurityCorsConfigurer.configurationSource(request -> {
                            var cors = new CorsConfiguration();
                            cors.setAllowedOrigins(corsConfigurationProperties.getAllowedOrigins());
                            cors.setAllowedMethods(corsConfigurationProperties.getAllowedMethods());
                            cors.setAllowedHeaders(corsConfigurationProperties.getAllowedHeaders());
                            return cors;
                        }))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests.anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer((oauth2) ->
                        oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)))
                .oauth2Login(Customizer.withDefaults())
                .logout(logout -> logout.addLogoutHandler(keycloakLogoutHandler).logoutSuccessUrl("/"));
        return http.build();
    }

    @Component
    @Slf4j
    public static class KeycloakLogoutHandler implements LogoutHandler {

        private final RestTemplate restTemplate;

        public KeycloakLogoutHandler(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        @Override
        public void logout(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
            logoutFromKeycloak((OidcUser) auth.getPrincipal());
        }

        private void logoutFromKeycloak(OidcUser user) {
            String endSessionEndpoint = user.getIssuer() + "/protocol/openid-connect/logout";
            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromUriString(endSessionEndpoint)
                    .queryParam("id_token_hint", user.getIdToken().getTokenValue());

            ResponseEntity<String> logoutResponse = restTemplate.getForEntity(
                    builder.toUriString(), String.class);
            if (logoutResponse.getStatusCode().is2xxSuccessful()) {
                log.info("Successfully logged out from Keycloak");
            } else {
                log.error("Could not propagate logout to Keycloak");
            }
        }
    }

    @Component
    @RequiredArgsConstructor
    @SuppressWarnings("unchecked")
    public static class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

        private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        private final JwtConverterProperties properties;

        @Override
        public AbstractAuthenticationToken convert(Jwt jwt) {
            Collection<GrantedAuthority> authorities = Stream.concat(
                    jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                    Stream.concat(
                            extractResourceRoles(jwt).stream(),
                            extractRealmRoles(jwt).stream()
                    )).collect(Collectors.toSet());
            return new JwtAuthenticationToken(jwt, authorities, getPrincipalClaimName(jwt));
        }

        private String getPrincipalClaimName(Jwt jwt) {
            String claimName = JwtClaimNames.SUB;
            if (properties.getPrincipalAttribute() != null) {
                claimName = properties.getPrincipalAttribute();
            }
            return jwt.getClaim(claimName);
        }

        private Collection<? extends GrantedAuthority> extractRealmRoles(Jwt jwt) {
            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
            if (realmAccess == null) {
                return Set.of();
            }
            Collection<String> realmRoles = (Collection<String>) realmAccess.get("roles");
            return mapRoles(realmRoles);
        }

        private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
            Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
            Map<String, Object> resource;
            Collection<String> resourceRoles;

            if (resourceAccess == null
                    || (resource = (Map<String, Object>) resourceAccess.get(properties.getResourceId())) == null
                    || (resourceRoles = (Collection<String>) resource.get("roles")) == null) {
                return Set.of();
            }
            return mapRoles(resourceRoles);
        }

        private static Set<SimpleGrantedAuthority> mapRoles(Collection<String> resourceRoles) {
            return resourceRoles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase().replace("-", "_")))
                    .collect(Collectors.toSet());
        }
    }
}
