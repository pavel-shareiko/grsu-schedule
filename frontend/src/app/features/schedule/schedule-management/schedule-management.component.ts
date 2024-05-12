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
import {PullTaskTrigger, SchedulePullStatus, SchedulePullTask} from "../types/task";
import {DatePipe, NgIf} from "@angular/common";
import {TimeagoIntl, TimeagoModule} from "ngx-timeago";
import {interval, startWith, switchMap} from "rxjs";
import {FormsModule} from "@angular/forms";
import {MatButton} from "@angular/material/button";

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
    TimeagoModule,
    FormsModule,
    MatButton,
    NgIf
  ],
  templateUrl: './schedule-management.component.html',
  styleUrl: './schedule-management.component.scss'
})
export class ScheduleManagementComponent implements OnInit {
  protected readonly PullTaskTrigger = PullTaskTrigger;
  protected readonly SchedulePullStatus = SchedulePullStatus;

  latestPullResult!: SchedulePullTask | null;
  latestPullTimestamp: Date | null = null;

  constructor(private scheduleService: ScheduleService) {
  }

  ngOnInit(): void {
    interval(90000)
      .pipe(
        startWith(0),
        switchMap(() => this.scheduleService.getLatestPullResult())
      )
      .subscribe({
        next: res => {
          const currentTimestamp = new Date();
          currentTimestamp.setSeconds(currentTimestamp.getSeconds() - 1);
          this.latestPullTimestamp = currentTimestamp;
          this.latestPullResult = res;
        }
      });
  }

  canSync(): boolean {
    return this.latestPullResult?.status === SchedulePullStatus.FAILED || this.latestPullResult?.status === SchedulePullStatus.COMPLETED
  }

  syncSchedule() {
    this.scheduleService.syncSchedule().subscribe({
      next: res => {
        this.latestPullResult = res;
      }
    });
  }
}
