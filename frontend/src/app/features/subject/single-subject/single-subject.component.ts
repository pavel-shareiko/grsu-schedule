import {Component, OnInit, ViewChild} from '@angular/core';
import {
  AnalysisHistoryTableComponent
} from "../../analysis-history/analysis-history-table/analysis-history-table.component";
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {
  MatExpansionPanel,
  MatExpansionPanelDescription,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle
} from "@angular/material/expansion";
import {MatIcon} from "@angular/material/icon";
import {ModuleCardGridComponent} from "../../analytics-module/module-card-grid/module-card-grid.component";
import {ModuleScope, ShortAnalyticsModuleInfo} from "../../../core/models/analytics-module";
import {AnalyticsModuleService} from "../../analytics-module/services/analytics-module.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {NotificationService} from "../../../core/services/notification.service";
import {AnalysisRerunDialog} from "../../analysis-history/analysis-history-table/dialogs/analysis-rerun.component";
import {SubjectService} from "../service/subject.service";
import {Subject} from "../type/subject";
import {SubjectCardService} from "../service/subject-card.service";
import {
  DynamicFormFieldComponent
} from "../../../core/components/form/dynamic-form/dynamic-form-field/dynamic-form-field.component";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgForOf} from "@angular/common";
import {MatFormField, MatHint, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {
  ConfirmationDialogComponent
} from "../../../core/components/dialog/confirmation-dialog/confirmation-dialog.component";
import {firstValueFrom} from "rxjs";
import {SubjectCardTranslator} from "../type/subject-card";

@Component({
  selector: 'app-single-subject',
  standalone: true,
  imports: [
    AnalysisHistoryTableComponent,
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatCardTitle,
    MatExpansionPanel,
    MatExpansionPanelDescription,
    MatExpansionPanelHeader,
    MatExpansionPanelTitle,
    MatIcon,
    ModuleCardGridComponent,
    DynamicFormFieldComponent,
    FormsModule,
    NgForOf,
    MatLabel,
    MatHint,
    MatFormField,
    MatInput,
    ReactiveFormsModule
  ],
  templateUrl: './single-subject.component.html',
  styleUrl: './single-subject.component.scss'
})
export class SingleSubjectComponent implements OnInit {
  public readonly subjectScope: ModuleScope[] = [ModuleScope.SUBJECT];
  private readonly LESSONS_SEQUENCE_REGEX = /^[лбпЛБП]*$/;

  subject!: Subject;

  subjectCardForm!: FormGroup;

  @ViewChild(AnalysisHistoryTableComponent) analysisHistoryTable!: AnalysisHistoryTableComponent;

  constructor(private subjectService: SubjectService,
              private subjectCardService: SubjectCardService,
              private analyticsModuleService: AnalyticsModuleService,
              private fb: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private dialog: MatDialog,
              private notification: NotificationService) {
  }

  async ngOnInit(): Promise<void> {
    const id = this.route.snapshot.paramMap.get("id")!;
    this.subject = await firstValueFrom(this.subjectService.getSubjectById(id)).catch(e => {
      this.notification.showError(e);
      this.router.navigate(["/subjects"]);
      throw e;
    });
    this.subjectCardForm = this.fb.group({
      lessonsSequence: [this.subject.subjectCard?.lessonsSequence, [Validators.pattern(this.LESSONS_SEQUENCE_REGEX)]]
    });
  }

  applyModule(module: ShortAnalyticsModuleInfo) {
    this.dialog.open(AnalysisRerunDialog, {
      data: {
        moduleName: module.systemName,
        initialState: {
          subjectId: this.subject.id,
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

  onSubjectCardFormSubmit() {
    this.subjectCardService.saveSubjectCard(this.subject.id, this.subjectCardForm.value.lessonsSequence)
      .subscribe({
        next: (res) => {
          this.subject.subjectCard = res;
          this.notification.showSuccess('Последовательность занятий для предмета была обновлена');
          this.subjectCardForm.reset({lessonsSequence: res.lessonsSequence});
        }
      });
  }

  onDeleteSubjectCard() {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: {
        title: 'Удаление последовательности занятий',
        message: 'Вы уверены, что хотите удалить последовательность занятий для предмета?'
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleteSubjectCard();
      }
    });
  }

  deleteSubjectCard() {
    this.subjectCardService.deleteSubjectCard(this.subject.id)
      .subscribe({
        next: () => {
          this.subject.subjectCard = undefined;
          this.notification.showSuccess('Последовательность занятий для предмета была удалена');
        }
      });
    this.subjectCardForm.reset()
  }

  parseLessonsSequence(sequence: string | undefined) {
    return new SubjectCardTranslator(sequence).translate()
  }
}
