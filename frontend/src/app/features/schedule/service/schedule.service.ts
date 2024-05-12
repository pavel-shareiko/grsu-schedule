import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LessonSearchResponse} from "../types/lesson";
import {Observable} from "rxjs";
import {SchedulePullTask} from "../types/task";

@Injectable({
  providedIn: 'root'
})
export class ScheduleService {

  constructor(private http: HttpClient) {
  }

  searchSchedule(filter: any, page = 0, rowsPerPage = 999): Observable<LessonSearchResponse> {
    return this.http.post<LessonSearchResponse>(`/api/v1/schedule/search?page=${page}&rowsPerPage=${rowsPerPage}`,
      filter);
  }

  getLatestPullResult(): Observable<SchedulePullTask> {
    return this.http.get<SchedulePullTask>('/api/v1/schedule/pull/latest');
  }

  syncSchedule(): Observable<SchedulePullTask> {
    return this.http.post<SchedulePullTask>('/api/v1/schedule/pull', {});
  }
}
