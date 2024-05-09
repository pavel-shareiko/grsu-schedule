import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
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
import {FormSubmitService} from "../../core/components/form/dynamic-form/services/form-submit.service";
import {
  AnalysisHistoryTableComponent
} from "../analysis-history/analysis-history-table/analysis-history-table.component";
import {filter, Subscription} from "rxjs";
import {NotificationService} from "../../core/services/notification.service";

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
export class AnalyticsModuleComponent implements OnInit, OnDestroy {
  meta: AnalyticsModuleMeta = {} as AnalyticsModuleMeta;
  @ViewChild(AnalysisHistoryTableComponent)
  analysisHistoryTable!: AnalysisHistoryTableComponent;

  constructor(private route: ActivatedRoute,
              private analyticsModuleService: AnalyticsModuleService,
              private analyticsModuleMetaService: AnalyticsModuleMetaService,
              private formSubmitService: FormSubmitService,
              private notificationService: NotificationService
  ) {
  }

  private analysisRerunSubscription!: Subscription;

  ngOnInit() {
    const moduleName = this.route.snapshot.paramMap.get('moduleName')!;
    this.analyticsModuleMetaService.getModuleMeta(moduleName)
      .subscribe(res => {
        this.meta = res;
      });
    this.analysisRerunSubscription = this.formSubmitService.formSubmitted$
      .pipe(filter(e => e.key === 'analysisRerun'))
      .subscribe(e => this.runAnalysis(e));
  }

  ngOnDestroy(): void {
    this.analysisRerunSubscription.unsubscribe();
  }

  runAnalysis($event: FormSubmittedEvent) {
    this.analyticsModuleService.performAnalysis(this.meta.moduleName, $event.value)
      .subscribe({
        next: () => {
          this.notificationService.showSuccess('Анализ запущен');
          $event.form.reset();
          this.analysisHistoryTable.loadResults();
        }
      })
  }
}
