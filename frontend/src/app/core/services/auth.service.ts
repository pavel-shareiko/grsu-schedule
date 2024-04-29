import {Injectable} from "@angular/core";
import {KeycloakService} from "keycloak-angular";

export interface IUserDetails {
  username: string;
  firstName: string;
  lastName: string;
  roles: string[];

  getDisplayedRole(): string;
}

class UserDetails implements IUserDetails {
  username: string;
  firstName: string;
  lastName: string;
  roles: string[];

  constructor(username: string, firstName: string, lastName: string, roles: string[]) {
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.roles = roles;
  }

  getDisplayedRole(): string {
    if (this.roles.includes("admin")) {
      return "Администратор";
    } else if (this.roles.includes("user")) {
      return "Пользователь";
    } else {
      return "Unknown";
    }
  }
}


@Injectable({providedIn: 'root'})
export class AuthService {
  private keycloak: KeycloakService;

  constructor(keycloak: KeycloakService) {
    this.keycloak = keycloak;
  }

  login(): Promise<void> {
    return this.keycloak.login();
  }

  logout(): Promise<void> {
    return this.keycloak.logout();
  }

  getCurrentUser(): IUserDetails {
    const keycloakInstance = this.keycloak.getKeycloakInstance();
    if (!keycloakInstance.authenticated) {
      throw new Error("User is not authenticated");
    }

    const token = keycloakInstance.tokenParsed;
    if (!token) {
      throw new Error("User is not authenticated");
    }

    const realmRoles = token.realm_access?.roles ?? [];
    return new UserDetails(
      token["preferred_username"],
      token["given_name"],
      token["family_name"],
      realmRoles
    );
  }
}
