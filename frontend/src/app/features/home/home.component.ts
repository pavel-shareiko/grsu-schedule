import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {ModuleCardComponent} from "../analytics-module/module-card/module-card.component";
import {ShortAnalyticsModuleInfo} from "../../core/models/analytics-module";
import {ModuleCardGridComponent} from "../analytics-module/module-card-grid/module-card-grid.component";
import {AnalyticsModuleService} from "../analytics-module/services/analytics-module.service";
import {JsonPipe} from "@angular/common";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    ModuleCardComponent,
    ModuleCardGridComponent,
    JsonPipe
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  modules: ShortAnalyticsModuleInfo[] = [];

  constructor(private moduleCardService: AnalyticsModuleService) {
  }

  ngOnInit(): void {
    this.moduleCardService.getAllModules()
      .subscribe(response => this.modules = response.modules);
  }
}
