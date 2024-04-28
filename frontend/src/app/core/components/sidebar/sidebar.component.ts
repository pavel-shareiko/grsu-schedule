import { Component } from '@angular/core';
import {SidebarHeaderComponent} from "./sidebar-header/sidebar-header.component";
import {SidebarNavigationComponent} from "./sidebar-navigation/sidebar-navigation.component";
import {SidebarFooterComponent} from "./sidebar-footer/sidebar-footer.component";

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    SidebarHeaderComponent,
    SidebarNavigationComponent,
    SidebarFooterComponent
  ],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {

}
