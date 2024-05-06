import {
  AfterViewInit,
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component, EventEmitter,
  Inject,
  Input,
  OnInit, Output,
  ViewChild
} from '@angular/core';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable,
  MatTableDataSource
} from "@angular/material/table";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {AnalysisHistoryService} from "../service/analysis-history.service";
import {AnalysisResult} from "../types/analysis-result";
import {DatePipe, JsonPipe} from "@angular/common";
import {MatIcon} from "@angular/material/icon";
import {MatButton, MatButtonModule, MatIconButton} from "@angular/material/button";
import {MatMenu, MatMenuItem, MatMenuTrigger} from "@angular/material/menu";
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogTitle
} from "@angular/material/dialog";
import {AnalyticsModuleMeta} from "../../analytics-module/types/meta";
import {
  DynamicFormComponent,
  FormSubmittedEvent
} from "../../../core/components/form/dynamic-form/dynamic-form.component";
import {DialogRef} from "@angular/cdk/dialog";
import {FormSubmitService} from "../../../core/components/form/dynamic-form/services/form-submit.service";

@Component({
  selector: 'app-analysis-history-table',
  standalone: true,
  imports: [
    MatColumnDef,
    MatTable,
    MatPaginator,
    MatHeaderCell,
    MatCell,
    MatHeaderCellDef,
    MatCellDef,
    MatHeaderRow,
    MatHeaderRowDef,
    MatRowDef,
    MatRow,
    DatePipe,
    MatIcon,
    MatButton,
    MatMenu,
    MatMenuItem,
    MatIconButton,
    MatMenuTrigger,
    JsonPipe
  ],
  templateUrl: './analysis-history-table.component.html',
  styleUrl: './analysis-history-table.component.scss'
})
export class AnalysisHistoryTableComponent implements OnInit {
  @Input({required: true}) meta!: AnalyticsModuleMeta;

  datasource: MatTableDataSource<AnalysisResult> = new MatTableDataSource<AnalysisResult>([])
  displayedColumns: string[] = ['id', 'createdBy', 'moduleName', 'context', 'result', 'status', 'createTimestamp', 'actions'];
  pageSize: number = 5;
  pageIndex: number = 0;
  totalElements: number = 0;

  constructor(private analysisHistoryService: AnalysisHistoryService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.loadResults();
  }

  onPageChange($event: PageEvent) {
    this.pageIndex = $event.pageIndex;
    this.pageSize = $event.pageSize;
    this.loadResults();
  }

  loadResults() {
    this.analysisHistoryService.searchResults(this.meta.moduleName, this.pageIndex, this.pageSize)
      .subscribe(res => {
        this.datasource.data = res.payload;
        this.totalElements = res.pagination.totalElements;
      });
  }

  showDetails(id: any) {

  }

  rerun(id: any) {
    const result = this.datasource.data.find(r => r.id === id)!;
    const dialogRef = this.dialog.open(AnalysisRerunDialog, {
      data: {
        meta: this.meta,
        initialState: result.context,
      },
      minWidth: '40vw'
    });

    dialogRef.afterClosed().subscribe({
      next: val => {
        console.log("Dialog closed with value: ", "'" + val + "'");
      }
    })
  }
}

@Component({
  selector: 'dialog-analysis-rerun',
  templateUrl: './dialogs/analysis-rerun.html',
  styleUrl: './dialogs/analysis-rerun.scss',
  standalone: true,
  imports: [MatButtonModule, MatDialogActions, MatDialogClose, MatDialogTitle, MatDialogContent, DynamicFormComponent],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AnalysisRerunDialog implements AfterViewInit {
  @ViewChild(DynamicFormComponent) dynamicForm!: DynamicFormComponent;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              private dialogRef: DialogRef<AnalysisRerunDialog>,
              private cd: ChangeDetectorRef,
              private formSubmitService: FormSubmitService
  ) {
  }

  ngAfterViewInit() {
    this.cd.detectChanges();
  }

  onRunClicked() {
    if (this.dynamicForm.form.valid) {
      const event = new FormSubmittedEvent(this.dynamicForm.formId, this.dynamicForm.form.value, this.dynamicForm.form)
      this.formSubmitService.formSubmitted(event);
      this.dialogRef.close();
    }
  }
}
