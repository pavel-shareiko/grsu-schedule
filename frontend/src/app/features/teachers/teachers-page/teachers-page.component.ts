import {Component} from '@angular/core';
import {
  MatExpansionPanel,
  MatExpansionPanelDescription,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle
} from "@angular/material/expansion";
import {TeacherListComponent} from "../teacher-list/teacher-list.component";
import {MatIcon} from "@angular/material/icon";

@Component({
  selector: 'app-teachers-page',
  standalone: true,
  imports: [
    MatExpansionPanel,
    MatExpansionPanelDescription,
    MatExpansionPanelTitle,
    MatExpansionPanelHeader,
    TeacherListComponent,
    MatIcon
  ],
  templateUrl: './teachers-page.component.html',
  styleUrl: './teachers-page.component.scss'
})
export class TeachersPageComponent {

}
