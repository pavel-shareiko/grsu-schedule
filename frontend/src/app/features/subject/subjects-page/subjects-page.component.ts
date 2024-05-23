import { Component } from '@angular/core';
import {FacultiesListComponent} from "../../faculty/faculties-list/faculties-list.component";
import {
  MatExpansionPanel,
  MatExpansionPanelDescription,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle
} from "@angular/material/expansion";
import {MatIcon} from "@angular/material/icon";
import {SubjectsListComponent} from "../subjects-list/subjects-list.component";

@Component({
  selector: 'app-subjects-page',
  standalone: true,
  imports: [
    FacultiesListComponent,
    MatExpansionPanel,
    MatExpansionPanelDescription,
    MatExpansionPanelHeader,
    MatExpansionPanelTitle,
    MatIcon,
    SubjectsListComponent
  ],
  templateUrl: './subjects-page.component.html',
  styleUrl: './subjects-page.component.scss'
})
export class SubjectsPageComponent {

}
