import { Component } from '@angular/core';
import {
    MatExpansionPanel,
    MatExpansionPanelDescription,
    MatExpansionPanelHeader,
    MatExpansionPanelTitle
} from "@angular/material/expansion";
import {MatIcon} from "@angular/material/icon";
import {TeacherListComponent} from "../../teachers/teacher-list/teacher-list.component";
import {FacultiesListComponent} from "../faculties-list/faculties-list.component";

@Component({
  selector: 'app-faculties-page',
  standalone: true,
  imports: [
    MatExpansionPanel,
    MatExpansionPanelDescription,
    MatExpansionPanelHeader,
    MatExpansionPanelTitle,
    MatIcon,
    TeacherListComponent,
    FacultiesListComponent
  ],
  templateUrl: './faculties-page.component.html',
  styleUrl: './faculties-page.component.scss'
})
export class FacultiesPageComponent {

}
