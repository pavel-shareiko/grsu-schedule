import {Component, Input} from '@angular/core';
import {ShortAnalyticsModuleInfo} from "../../../core/models/analytics-module";
import {ModuleCardComponent} from "../module-card/module-card.component";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-module-card-grid',
  standalone: true,
  imports: [
    ModuleCardComponent,
    NgForOf
  ],
  templateUrl: './module-card-grid.component.html',
  styleUrl: './module-card-grid.component.scss'
})
export class ModuleCardGridComponent {
  @Input() modules: ShortAnalyticsModuleInfo[] = [];
}
