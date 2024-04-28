import {Component} from '@angular/core';
import {AuthService, IUserDetails} from "../../../services/auth.service";
import {MatIcon} from "@angular/material/icon";

@Component({
  selector: 'app-sidebar-footer',
  standalone: true,
  imports: [
    MatIcon
  ],
  templateUrl: './sidebar-footer.component.html',
  styleUrl: './sidebar-footer.component.scss'
})
export class SidebarFooterComponent {
  public user: IUserDetails;
  public authService: AuthService;

  constructor(authService: AuthService) {
    this.user = authService.getCurrentUser();
    this.authService = authService;
  }

}
