import {APP_INITIALIZER, NgModule} from "@angular/core";
import {initializeKeycloak} from "./init/keycloak-init.factory";
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService]
    }
  ],
  imports: [
    KeycloakAngularModule,
    HttpClientModule
  ]
})
export class CoreModule {
}
