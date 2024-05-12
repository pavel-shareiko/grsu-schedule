import {Component, OnInit} from '@angular/core';
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
import {ScheduleService} from "../service/schedule.service";
import {SchedulePullTask} from "../types/task";
import {DatePipe} from "@angular/common";
import {TimeagoModule} from "ngx-timeago";

@Component({
  selector: 'app-schedule-management',
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
    DatePipe,
    TimeagoModule
  ],
  templateUrl: './schedule-management.component.html',
  styleUrl: './schedule-management.component.scss'
})
export class ScheduleManagementComponent implements OnInit {
  latestPullResult!: SchedulePullTask | null;

  constructor(private scheduleService: ScheduleService) {
  }

  ngOnInit(): void {
    this.scheduleService.getLatestPullResult().subscribe(
      {
        next: res => {
          console.log(res);
          this.latestPullResult = res;
        }
      }
    );
  }

}
