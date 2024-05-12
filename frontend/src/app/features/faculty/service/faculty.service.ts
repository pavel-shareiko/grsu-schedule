import {Injectable} from '@angular/core';
import {FacultiesSearchFilter, FacultiesSearchResponse} from "../types/faculty";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class FacultyService {

  constructor(private http: HttpClient) {
  }

  getFaculties(filter: FacultiesSearchFilter, pageIndex: number, pageSize: number) {
    return this.http.post<FacultiesSearchResponse>(
      `/api/v1/faculties/search?page=${pageIndex}&rowsPerPage=${pageSize}`, {
        ...filter
      })
  }
}
