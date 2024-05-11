import {Component, OnInit, ViewChild} from '@angular/core';
import {
  MatAccordion,
  MatExpansionPanel,
  MatExpansionPanelDescription,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle
} from "@angular/material/expansion";
import {TeacherService} from "../service/teacher.service";
import {AnalyticsModuleService} from "../../analytics-module/services/analytics-module.service";
import {ScheduleService} from "../../schedule/service/schedule.service";
import {ActivatedRoute, Router} from "@angular/router";
import {NotificationService} from "../../../core/services/notification.service";
import {Teacher} from "../types/teacher";
import {Lesson} from "../../schedule/types/lesson";
import {ModuleCardGridComponent} from "../../analytics-module/module-card-grid/module-card-grid.component";
import {ModuleScope, ShortAnalyticsModuleInfo} from "../../../core/models/analytics-module";
import {MatButton} from "@angular/material/button";
import {MatDialog} from "@angular/material/dialog";
import {AnalysisRerunDialog} from "../../analysis-history/analysis-history-table/dialogs/analysis-rerun.component";
import {MatIcon} from "@angular/material/icon";
import {
  MatCard,
  MatCardActions,
  MatCardContent,
  MatCardFooter,
  MatCardHeader,
  MatCardSubtitle,
  MatCardTitle
} from "@angular/material/card";
import {NgIf} from "@angular/common";
import {
  AnalysisHistoryTableComponent
} from "../../analysis-history/analysis-history-table/analysis-history-table.component";

@Component({
  selector: 'app-single-teacher',
  standalone: true,
  imports: [
    MatAccordion,
    MatExpansionPanel,
    MatExpansionPanelTitle,
    MatExpansionPanelHeader,
    MatExpansionPanelDescription,
    ModuleCardGridComponent,
    MatButton,
    MatIcon,
    MatCard,
    MatCardHeader,
    MatCardContent,
    MatCardTitle,
    MatCardSubtitle,
    MatCardActions,
    MatCardFooter,
    NgIf,
    AnalysisHistoryTableComponent,
  ],
  templateUrl: './single-teacher.component.html',
  styleUrl: './single-teacher.component.scss'
})
export class SingleTeacherComponent implements OnInit {
  readonly teacherScope: ModuleScope[] = [ModuleScope.TEACHER];

  teacher!: Teacher;

  @ViewChild(AnalysisHistoryTableComponent) analysisHistoryTable!: AnalysisHistoryTableComponent;

  constructor(private teacherService: TeacherService,
              private analyticsModuleService: AnalyticsModuleService,
              private route: ActivatedRoute,
              private router: Router,
              private dialog: MatDialog,
              private notification: NotificationService) {
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get("id")!;
    this.teacherService.getTeacherById(id).subscribe({
      next: t => this.teacher = t,
      error: e => {
        this.notification.showError(e);
        this.router.navigate(["/teachers"]);
      }
    });
  }

  applyModule(module: ShortAnalyticsModuleInfo) {
    this.dialog.open(AnalysisRerunDialog, {
      data: {
        moduleName: module.systemName,
        initialState: {
          teacherId: this.teacher.id,
        },
        onRunClicked: this.onModuleRun.bind(this),
      },
      minWidth: '40vw',
    });
  }

  onModuleRun(dialog: AnalysisRerunDialog) {
    this.analyticsModuleService.performAnalysis(dialog.meta.moduleName, dialog.dynamicForm.form.value)
      .subscribe({
        next: () => {
          this.notification.showSuccess('Анализ запущен');
          dialog.dynamicForm.form.reset();
          this.analysisHistoryTable.loadResults();
        }
      })
  }
}
