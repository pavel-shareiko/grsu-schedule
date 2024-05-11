import {Injectable} from '@angular/core';
import {map, Observable} from "rxjs";
import {TeacherSearchFilter, TeacherSearchResponse} from "../types/teacher";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class TeacherService {

  constructor(private http: HttpClient) {
  }

  getTeachers(filter: TeacherSearchFilter, page: number = 0, rowsPerPage: number = 10): Observable<TeacherSearchResponse> {
    return this.http.post<TeacherSearchResponse>(
      `/api/v1/teachers/search?page=${page}&rowsPerPage=${rowsPerPage}`, {
        ...filter
      })
  }

  getTeacherById(id: string) {
    return this.getTeachers({id}).pipe(
      map(teachers => {
        if (teachers.payload.length === 0) {
          throw new Error(`Преподаватель с id ${id} не найден`)
        }
        return teachers.payload[0];
      })
    );
  }
}
