import {Injectable} from '@angular/core';
import {FacultiesSearchFilter, FacultiesSearchResponse, FacultySearchItem} from "../types/faculty";
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FacultyService {

  constructor(private http: HttpClient) {
  }

  getFacultyById(id: string): Observable<FacultySearchItem> {
    return this.getFaculties({id: +id}, 0, 1)
      .pipe(
        map(response => response.payload[0])
      );
  }

  getFaculties(filter: FacultiesSearchFilter, pageIndex: number = 0, pageSize: number = 10) {
    return this.http.post<FacultiesSearchResponse>(
      `/api/v1/faculties/search?page=${pageIndex}&rowsPerPage=${pageSize}`, {
        ...filter
      })
  }
}
