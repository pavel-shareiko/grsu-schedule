import {ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {KeycloakAuthGuard, KeycloakService} from "keycloak-angular";
import {Injectable} from "@angular/core";
import {NotificationService} from "../services/notification.service";
import {RoleConstants} from "../utils/constants";

@Injectable({
  providedIn: 'root'
})
export class AdminGuard extends KeycloakAuthGuard {

  constructor(
    protected override readonly router: Router,
    protected readonly keycloak: KeycloakService,
    protected readonly notification: NotificationService
  ) {
    super(router, keycloak);
  }

  async isAccessAllowed(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Promise<boolean | UrlTree> {

    if (!this.authenticated) {
      await this.keycloak.login({
        redirectUri: window.location.origin + state.url,
      });
    }

    if (this.keycloak.isUserInRole(RoleConstants.admin.systemName)) {
      return true;
    }

    this.notification.showError('У вас нет прав для доступа к этой странице');
    return false;
  }
}
