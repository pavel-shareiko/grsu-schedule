import {Component, OnInit, ViewChild} from '@angular/core';
import {DynamicFormComponent, FormSubmittedEvent} from "../../core/components/form/dynamic-form/dynamic-form.component";
import {ActivatedRoute} from "@angular/router";
import {AnalyticsModuleMetaService} from "./services/analytics-module-meta.service";
import {AnalyticsModuleMeta} from "./types/meta";
import {AsyncPipe, NgIf} from "@angular/common";
import {ModuleCardGridComponent} from "./module-card-grid/module-card-grid.component";
import {AnalyticsModuleService} from "./services/analytics-module.service";
import {MatIcon} from "@angular/material/icon";
import {
  MatExpansionPanel,
  MatExpansionPanelDescription,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle
} from "@angular/material/expansion";
import {
  AnalysisHistoryTableComponent
} from "../analysis-history/analysis-history-table/analysis-history-table.component";

@Component({
  selector: 'app-analytics-module',
  standalone: true,
  imports: [
    DynamicFormComponent,
    AsyncPipe,
    NgIf,
    ModuleCardGridComponent,
    MatIcon,
    MatExpansionPanel,
    MatExpansionPanelHeader,
    MatExpansionPanelTitle,
    MatExpansionPanelDescription,
    AnalysisHistoryTableComponent,
  ],
  templateUrl: './analytics-module.component.html',
  styleUrl: './analytics-module.component.scss'
})
export class AnalyticsModuleComponent implements OnInit {
  meta: AnalyticsModuleMeta = {} as AnalyticsModuleMeta;
  @ViewChild(AnalysisHistoryTableComponent)
  analysisHistoryTable!: AnalysisHistoryTableComponent;

  constructor(private route: ActivatedRoute,
              private analyticsModuleService: AnalyticsModuleService,
              private analyticsModuleMetaService: AnalyticsModuleMetaService,
  ) {
  }

  ngOnInit() {
    const moduleName = this.route.snapshot.paramMap.get('moduleName')!;
    this.analyticsModuleMetaService.getModuleMeta(moduleName)
      .subscribe(res => {
        this.meta = res;
      });
  }

  runAnalysis($event: FormSubmittedEvent) {
    this.analyticsModuleService.performAnalysis(this.meta.moduleName, $event.value)
      .subscribe(res => {
        $event.form.reset();
        this.analysisHistoryTable.loadResults();
      });
  }
}
