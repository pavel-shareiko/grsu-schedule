import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ModuleScope, ShortAnalyticsModuleInfo} from "../../../core/models/analytics-module";
import {Observable} from "rxjs";
import {environment} from "../../../../environments/environment";

export type AnalyticsModuleSearchResponse = {
  modules: ShortAnalyticsModuleInfo[]
}

@Injectable({
  providedIn: 'root'
})
export class AnalyticsModuleService {
  apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {
  }

  getAllModules(): Observable<AnalyticsModuleSearchResponse> {
    return this.http.post<AnalyticsModuleSearchResponse>(`${this.apiUrl}/api/v1/analytics-modules/search`, {
      scope: []
    });
  }

  getModulesWithScope(scope: ModuleScope[]): Observable<AnalyticsModuleSearchResponse> {
    return this.http.post<AnalyticsModuleSearchResponse>(`${this.apiUrl}/api/v1/analytics-modules/search`, {
      scope: scope.map(s => ModuleScope[s])
    });
  }

  performAnalysis(moduleName: string, data: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/api/v1/analytics-modules/${moduleName}/perform`, data);
  }
}
