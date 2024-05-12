import {Component, ViewChild} from '@angular/core';
import {
  AnalysisHistoryTableComponent
} from "../../analysis-history/analysis-history-table/analysis-history-table.component";
import {MatCard, MatCardContent, MatCardHeader, MatCardSubtitle, MatCardTitle} from "@angular/material/card";
import {
  MatExpansionPanel,
  MatExpansionPanelDescription,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle
} from "@angular/material/expansion";
import {MatIcon} from "@angular/material/icon";
import {ModuleCardGridComponent} from "../../analytics-module/module-card-grid/module-card-grid.component";
import {ScheduleTableComponent} from "../../schedule/schedule-table/schedule-table.component";
import {ModuleScope, ShortAnalyticsModuleInfo} from "../../../core/models/analytics-module";
import {Teacher} from "../../teachers/types/teacher";
import {TeacherService} from "../../teachers/service/teacher.service";
import {AnalyticsModuleService} from "../../analytics-module/services/analytics-module.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {NotificationService} from "../../../core/services/notification.service";
import {AnalysisRerunDialog} from "../../analysis-history/analysis-history-table/dialogs/analysis-rerun.component";
import {Faculty, FacultySearchItem} from "../types/faculty";
import {FacultyService} from "../service/faculty.service";

@Component({
  selector: 'app-single-faculty',
  standalone: true,
  imports: [
    AnalysisHistoryTableComponent,
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatCardSubtitle,
    MatCardTitle,
    MatExpansionPanel,
    MatExpansionPanelDescription,
    MatExpansionPanelHeader,
    MatExpansionPanelTitle,
    MatIcon,
    ModuleCardGridComponent,
    ScheduleTableComponent
  ],
  templateUrl: './single-faculty.component.html',
  styleUrl: './single-faculty.component.scss'
})
export class SingleFacultyComponent {
  readonly facultyScope: ModuleScope[] = [ModuleScope.FACULTY];

  faculty!: FacultySearchItem;

  @ViewChild(AnalysisHistoryTableComponent) analysisHistoryTable!: AnalysisHistoryTableComponent;

  constructor(private facultyService: FacultyService,
              private analyticsModuleService: AnalyticsModuleService,
              private route: ActivatedRoute,
              private router: Router,
              private dialog: MatDialog,
              private notification: NotificationService) {
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get("id")!;
    this.facultyService.getFacultyById(id).subscribe({
      next: f => this.faculty = f,
      error: e => {
        this.notification.showError(e);
        this.router.navigate(["/faculties"]);
      }
    });
  }

  applyModule(module: ShortAnalyticsModuleInfo) {
    this.dialog.open(AnalysisRerunDialog, {
      data: {
        moduleName: module.systemName,
        initialState: {
          facultyId: this.faculty.id,
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
