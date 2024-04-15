package by.grsu.schedule.gateway.geo.locationiq;

import by.grsu.schedule.configuration.properties.LocationIqApiProperties;
import by.grsu.schedule.domain.GeocodingQueryHistoryEntity;
import by.grsu.schedule.dto.AddressDto;
import by.grsu.schedule.gateway.geo.AddressQueryBuilder;
import by.grsu.schedule.gateway.geo.GeoApiGateway;
import by.grsu.schedule.persistence.Coordinate;
import by.grsu.schedule.service.GeocodingQueryHistoryService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.util.concurrent.RateLimiter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@ConditionalOnProperty(value = "application.services.location-iq.enabled", havingValue = "true")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@SuppressWarnings("UnstableApiUsage")
public class LocationIqGeoApiGateway implements GeoApiGateway {
    String apiKey;
    String addressFormat;
    RestClient restClient;
    RateLimiter rateLimiter;
    GeocodingQueryHistoryService geocodingQueryHistoryService;

    public LocationIqGeoApiGateway(LocationIqApiProperties locationIqApiProperties,
                                   GeocodingQueryHistoryService geocodingQueryHistoryService) {
        this.apiKey = locationIqApiProperties.getApiKey();
        this.restClient = RestClient.builder()
                .baseUrl(locationIqApiProperties.getUrl())
                .build();
        this.addressFormat = locationIqApiProperties.getAddressFormat();
        this.geocodingQueryHistoryService = geocodingQueryHistoryService;
        this.rateLimiter = RateLimiter.create(locationIqApiProperties.getRateLimit());
    }

    @Override
    public Coordinate getAddressLocation(AddressDto address) {
        AddressQueryBuilder queryBuilder = new AddressQueryBuilder();
        String addressQuery = queryBuilder.buildAddressQuery(address, addressFormat);
        if (addressQuery == null) {
            return null;
        }

        Optional<Coordinate> cachedCoordinate =
                geocodingQueryHistoryService.findByQueryIgnoreCase(addressQuery)
                        .map(GeocodingQueryHistoryEntity::getLocation);

        if (cachedCoordinate.isPresent()) {
            log.info("Coordinate for address {} is found in cache", address);
            return cachedCoordinate.get();
        }

        List<LocationIqForwardGeocodingResponse> apiResponse;
        try {
            apiResponse = performForwardGeocodingRequest(addressQuery);
        } catch (HttpClientErrorException.NotFound e) {
            log.error("LocationIQ API response for address {} is not found", address);
            return null;
        } catch (HttpClientErrorException.TooManyRequests e) {
            log.error("LocationIQ API rate limit exceeded", e);
            return null;
        }

        if (apiResponse == null || apiResponse.isEmpty()) {
            log.error("LocationIQ API response for address {} is null", address);
            return null;
        }

        return geocodingQueryHistoryService.saveQueryHistory(
                        addressQuery,
                        Coordinate.of(apiResponse.getFirst().lat(), apiResponse.getFirst().lon()))
                .getLocation();
    }

    private List<LocationIqForwardGeocodingResponse> performForwardGeocodingRequest(String addressQuery) {
        if (!rateLimiter.tryAcquire()) {
            log.warn("Requests to LocationIQ API are sent too fast. Rate limit exceeded. Waiting...");
        }
        rateLimiter.acquire();

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/search")
                        .queryParam("key", apiKey)
                        .queryParam("format", "json")
                        .queryParam("limit", 1)
                        .queryParam("countrycodes", "by")
                        .queryParam("q", addressQuery)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    private record LocationIqForwardGeocodingResponse(
            Double lat,
            Double lon,
            @JsonProperty("display_name") String displayName
    ) {
    }
}
