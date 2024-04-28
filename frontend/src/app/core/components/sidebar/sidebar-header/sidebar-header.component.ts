import {Component, ViewEncapsulation} from '@angular/core';
import {NgOptimizedImage} from "@angular/common";
import {MatIcon} from "@angular/material/icon";

@Component({
  selector: 'app-sidebar-header',
  standalone: true,
  imports: [
    NgOptimizedImage,
  ],
  templateUrl: './sidebar-header.component.html',
  styleUrl: './sidebar-header.component.scss'
})
export class SidebarHeaderComponent {

}
