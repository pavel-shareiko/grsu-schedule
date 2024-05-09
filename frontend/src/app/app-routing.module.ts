import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {AuthGuard} from "./core/guard/auth.guard";
import {HomeComponent} from "./features/home/home.component";
import {AnalyticsModuleComponent} from "./features/analytics-module/analytics-module.component";
import {AnalysisResultComponent} from "./features/analysis-result/analysis-result.component";

const routes: Routes = [
  {path: '', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'modules/:moduleName', component: AnalyticsModuleComponent, canActivate: [AuthGuard]},
  {path: 'results/:id', component: AnalysisResultComponent, canActivate: [AuthGuard]},
  {path: '**', redirectTo: ''}
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
