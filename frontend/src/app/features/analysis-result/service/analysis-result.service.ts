import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AnalysisResultSearchResponse} from "../types/analysis-result";

@Injectable({
  providedIn: 'root'
})
export class AnalysisResultService {

  constructor(private http: HttpClient) {
  }

  searchResultsForModule(moduleName: string, page = 0, rowsPerPage = 10): Observable<AnalysisResultSearchResponse> {
    return this.http.post<AnalysisResultSearchResponse>(
      `/api/v1/analysis-results/search?page=${page}&rowsPerPage=${rowsPerPage}`, {
        moduleName
      });
  }

  findResultById(id: number): Observable<AnalysisResultSearchResponse> {
    return this.http.post<AnalysisResultSearchResponse>(
      `/api/v1/analysis-results/search`,
      {id}
    )
  }
}
