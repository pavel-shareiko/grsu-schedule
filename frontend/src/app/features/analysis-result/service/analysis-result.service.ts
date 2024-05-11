import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AnalysisResultSearchResponse} from "../types/analysis-result";
import {AnalysisStatus} from "../../../core/models/analytics-module";

export type AnalysisResultFilter = {
  id?: number;
  moduleName?: string;
  status?: AnalysisStatus,
  context?: any;
}

@Injectable({
  providedIn: 'root'
})
export class AnalysisResultService {

  constructor(private http: HttpClient) {
  }

  searchResultsForModule(moduleName: string, page = 0, rowsPerPage = 10): Observable<AnalysisResultSearchResponse> {
    return this.searchResults({moduleName}, page, rowsPerPage);
  }

  findResultById(id: number): Observable<AnalysisResultSearchResponse> {
    return this.searchResults({id});
  }

  searchResults(filter: AnalysisResultFilter, page = 0, rowsPerPage = 10) {
    return this.http.post<AnalysisResultSearchResponse>(
      `/api/v1/analysis-results/search?page=${page}&rowsPerPage=${rowsPerPage}`, filter
    );
  }
}
