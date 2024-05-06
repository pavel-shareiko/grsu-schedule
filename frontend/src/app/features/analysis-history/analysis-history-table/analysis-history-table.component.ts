import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
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
import {Pagination} from "../../../core/types/pagination";
import {DatePipe} from "@angular/common";
import {MatIcon} from "@angular/material/icon";
import {retry} from "rxjs";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatMenu, MatMenuItem, MatMenuTrigger} from "@angular/material/menu";

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
    MatMenuTrigger
  ],
  templateUrl: './analysis-history-table.component.html',
  styleUrl: './analysis-history-table.component.scss'
})
export class AnalysisHistoryTableComponent implements OnInit {
  @Input({required: true}) module!: string;

  datasource: MatTableDataSource<AnalysisResult> = new MatTableDataSource<AnalysisResult>([])
  displayedColumns: string[] = ['id', 'createdBy', 'status', 'createTimestamp', 'actions'];
  pageSize: number = 5;
  pageIndex: number = 0;
  totalElements: number = 0;

  constructor(private analysisHistoryService: AnalysisHistoryService) {
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
    this.analysisHistoryService.searchResults(this.module, this.pageIndex, this.pageSize)
      .subscribe(res => {
        this.datasource.data = res.payload;
        this.totalElements = res.pagination.totalElements;
      });
  }

  showDetails(id: any) {

  }

  retry(id: any) {

  }
}
