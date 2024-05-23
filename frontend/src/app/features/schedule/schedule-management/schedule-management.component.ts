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
import {DatePipe, NgClass, NgIf} from "@angular/common";
import {TimeagoModule} from "ngx-timeago";
import {interval, startWith, switchMap} from "rxjs";
import {FormsModule} from "@angular/forms";
import {MatButton} from "@angular/material/button";
import {MatDialog} from "@angular/material/dialog";
import {
  ConfirmationDialogComponent
} from "../../../core/components/dialog/confirmation-dialog/confirmation-dialog.component";

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
    NgIf,
    NgClass
  ],
  templateUrl: './schedule-management.component.html',
  styleUrl: './schedule-management.component.scss'
})
export class ScheduleManagementComponent implements OnInit {
  protected readonly PullTaskTrigger = PullTaskTrigger;
  protected readonly SchedulePullStatus = SchedulePullStatus;

  latestPullResult!: SchedulePullTask | null;
  latestPullTimestamp: Date | null = null;

  constructor(private scheduleService: ScheduleService,
              private dialog: MatDialog) {
  }

  ngOnInit(): void {
    interval(90000)
      .pipe(
        startWith(0),
        switchMap(() => this.scheduleService.getLatestTask())
      )
      .subscribe({
        next: res => {
          this.updateLatestPullResult(res);
        }
      });
  }

  private updateLatestPullResult(res: SchedulePullTask) {
    const currentTimestamp = new Date();
    currentTimestamp.setSeconds(currentTimestamp.getSeconds() - 1);
    this.latestPullTimestamp = currentTimestamp;
    this.latestPullResult = res;
  }

  canSync(): boolean {
    return this.latestPullResult?.status !== SchedulePullStatus.PENDING && this.latestPullResult?.status !== SchedulePullStatus.IN_PROGRESS;
  }

  syncSchedule() {
    this.scheduleService.getLatestTask().subscribe({
      next: res => {
        this.updateLatestPullResult(res);
        if (!this.canSync()) {
          const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
            width: '250px',
            data: {
              message: 'Существует уже запланированная задача на синхронизацию расписания. ' +
                'Вы уверены, что хотите создать новую задачу?',
              title: 'Подтверждение действия'
            }
          });

          dialogRef.afterClosed().subscribe(result => {
            if (result) {
              this.doSync();
            }
          });
        } else {
          this.doSync();
        }
      }
    });
  }

  private doSync() {
    this.scheduleService.syncSchedule().subscribe({
      next: res => {
        this.latestPullResult = res;
      }
    });
  }
}
