import {Injectable} from "@angular/core";
import {KeycloakService} from "keycloak-angular";
import {RoleConstants} from "../utils/constants";

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
    for (const roleDefinition of RoleConstants.applicationRoles) {
      if (this.roles.includes(roleDefinition.systemName)) {
        return roleDefinition.displayName;
      }
    }

    return "Unknown";
  }
}


@Injectable({providedIn: 'root'})
export class AuthService {
  private keycloak: KeycloakService;

  constructor(keycloak: KeycloakService) {
    this.keycloak = keycloak;
  }

  hasRole(role: string): boolean {
    return this.keycloak.isUserInRole(role);
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
