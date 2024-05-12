import {Component, Input, OnInit} from '@angular/core';
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
import {AnalysisResultService} from "../../analysis-result/service/analysis-result.service";
import {AnalysisResult} from "../../analysis-result/types/analysis-result";
import {DatePipe, JsonPipe} from "@angular/common";
import {MatIcon} from "@angular/material/icon";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatMenu, MatMenuItem, MatMenuTrigger} from "@angular/material/menu";
import {MatDialog} from "@angular/material/dialog";
import {Router, RouterLink} from "@angular/router";
import {AnalysisRerunDialog} from "./dialogs/analysis-rerun.component";

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
    JsonPipe,
    RouterLink
  ],
  templateUrl: './analysis-history-table.component.html',
  styleUrl: './analysis-history-table.component.scss'
})
export class AnalysisHistoryTableComponent implements OnInit {
  @Input() filter!: any;

  datasource: MatTableDataSource<AnalysisResult> = new MatTableDataSource<AnalysisResult>([])
  displayedColumns: string[] = ['id', 'createdBy', 'moduleName', 'context', 'result', 'status', 'createTimestamp', 'actions'];
  pageSize: number = 5;
  pageIndex: number = 0;
  totalElements: number = 0;

  constructor(private analysisHistoryService: AnalysisResultService,
              private dialog: MatDialog,
              private router: Router
  ) {
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
    this.analysisHistoryService.searchResults(this.filter, this.pageIndex, this.pageSize)
      .subscribe(res => {
        this.datasource.data = res.payload;
        this.totalElements = res.pagination.totalElements;
      });
  }

  showDetails(id: number) {
    this.router.navigate(['/results', id]);
  }

  rerun(id: any) {
    const analysisResult = this.datasource.data.find(r => r.id === id)!;
    this.dialog.open(AnalysisRerunDialog, {
      data: {
        moduleName: analysisResult.moduleName,
        initialState: analysisResult.context,
      },
      minWidth: '40vw'
    });
  }
}
