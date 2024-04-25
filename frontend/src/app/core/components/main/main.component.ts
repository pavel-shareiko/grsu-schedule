import {Component, OnInit} from '@angular/core';
import {KeycloakService} from "keycloak-angular";
import {KeycloakProfile} from "keycloak-js";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss'
})
export class MainComponent implements OnInit {
  protected user: KeycloakProfile = {} as KeycloakProfile;

  private keycloakService: KeycloakService;

  constructor(keycloakService: KeycloakService) {
    this.keycloakService = keycloakService;
  }

  ngOnInit(): void {
    this.keycloakService.loadUserProfile()
      .then(user => this.user = user);
  }
}
