import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Subject, SubjectSearchFilter, SubjectSearchResponse} from "../type/subject";

@Injectable({
  providedIn: 'root'
})
export class SubjectService {

  constructor(private http: HttpClient) {
  }

  getSubjectById(id: string): Observable<Subject> {
    return this.getSubjects({id: +id}, 0, 1)
      .pipe(
        map(response => response.payload[0])
      );
  }

  getSubjects(filter: SubjectSearchFilter, pageIndex: number = 0, pageSize: number = 10) {
    return this.http.post<SubjectSearchResponse>(
      `/api/v1/subjects/search?page=${pageIndex}&rowsPerPage=${pageSize}`, {
        ...filter
      })
  }
}
