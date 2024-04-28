import {Component} from '@angular/core';
import {MatList, MatListItem} from "@angular/material/list";
import {RouterLink, RouterLinkActive} from "@angular/router";
import {MatIcon} from "@angular/material/icon";

@Component({
  selector: 'app-sidebar-navigation',
  standalone: true,
  imports: [
    MatList,
    MatListItem,
    RouterLink,
    RouterLinkActive,
    MatIcon
  ],
  templateUrl: './sidebar-navigation.component.html',
  styleUrl: './sidebar-navigation.component.scss'
})
export class SidebarNavigationComponent {

  constructor() {
  }

}