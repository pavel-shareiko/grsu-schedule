import {Component, Input, OnInit} from '@angular/core';
import {DatePipe, KeyValuePipe, NgClass, NgForOf, NgIf} from "@angular/common";
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
  MatTable
} from "@angular/material/table";
import {LessonSearchItem} from "../types/lesson";
import {ScheduleService} from "../service/schedule.service";
import {TimePipe} from "../../../core/pipes/time.pipe";
import {RouterLink} from "@angular/router";
import {MatIcon} from "@angular/material/icon";

@Component({
  selector: 'app-schedule-table',
  standalone: true,
  imports: [
    NgForOf,
    KeyValuePipe,
    MatTable,
    MatColumnDef,
    MatHeaderCellDef,
    MatHeaderCell,
    MatCellDef,
    MatCell,
    MatHeaderRow,
    MatRow,
    MatRowDef,
    MatHeaderRowDef,
    NgIf,
    DatePipe,
    TimePipe,
    RouterLink,
    MatIcon,
    NgClass
  ],
  templateUrl: './schedule-table.component.html',
  styleUrl: './schedule-table.component.scss'
})
export class ScheduleTableComponent implements OnInit {
  @Input() filter!: any;

  lessonsByDate: { [date: string]: LessonSearchItem[] } = {};

  constructor(private scheduleService: ScheduleService) {
  }

  ngOnInit() {
    this.scheduleService.searchSchedule(this.filter).subscribe({
      next: res => {
        this.groupLessonsByDate(res.payload);
      }
    });
  }

  groupLessonsByDate(lessons: LessonSearchItem[]) {
    this.lessonsByDate = lessons.reduce((grouped: { [date: string]: LessonSearchItem[] }, lesson) => {
      const date = lesson.date;
      if (!grouped[date]) {
        grouped[date] = [];
      }
      grouped[date].push(lesson);
      return grouped;
    }, {});
  }

  getDayOfWeek(dateString: string) {
    const date = new Date(dateString);
    return date.toLocaleString('ru-RU', {weekday: 'long'});
  }

  hasLessons() {
    return Object.keys(this.lessonsByDate).length > 0;
  }
}
