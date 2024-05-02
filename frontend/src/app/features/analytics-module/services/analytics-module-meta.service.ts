import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../../environments/environment";
import {AnalyticsModuleMeta} from "../types/meta";

@Injectable({
  providedIn: 'root'
})
export class AnalyticsModuleMetaService {
  apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {
  }

  public getModuleMeta(moduleName: string): Observable<AnalyticsModuleMeta> {
    return this.http.get<AnalyticsModuleMeta>(`${this.apiUrl}/api/v1/analytics-modules-meta/${moduleName}`);
  }
}
