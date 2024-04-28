import {Component, ViewEncapsulation} from '@angular/core';
import {NgOptimizedImage} from "@angular/common";
import {MatIcon} from "@angular/material/icon";
import {MatIconButton} from "@angular/material/button";
import {MatToolbar} from "@angular/material/toolbar";

@Component({
  selector: 'app-sidebar-header',
  standalone: true,
  imports: [
    NgOptimizedImage,
    MatIcon,
    MatIconButton,
    MatToolbar,
  ],
  templateUrl: './sidebar-header.component.html',
  styleUrl: './sidebar-header.component.scss'
})
export class SidebarHeaderComponent {

}
