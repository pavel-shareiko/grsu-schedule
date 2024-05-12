import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {AuthGuard} from "./core/guard/auth.guard";
import {HomeComponent} from "./features/home/home.component";
import {AnalyticsModuleComponent} from "./features/analytics-module/analytics-module.component";
import {AnalysisResultComponent} from "./features/analysis-result/analysis-result.component";
import {TeachersPageComponent} from "./features/teachers/teachers-page/teachers-page.component";
import {SingleTeacherComponent} from "./features/teachers/single-teacher/single-teacher.component";
import {FacultiesPageComponent} from "./features/faculty/faculties-page/faculties-page.component";
import {SingleFacultyComponent} from "./features/faculty/single-faculty/single-faculty.component";

const routes: Routes = [
  {path: '', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'modules/:moduleName', component: AnalyticsModuleComponent, canActivate: [AuthGuard]},
  {path: 'results/:id', component: AnalysisResultComponent, canActivate: [AuthGuard]},
  {path: 'teachers', component: TeachersPageComponent, canActivate: [AuthGuard]},
  {path: 'teachers/:id', component: SingleTeacherComponent, canActivate: [AuthGuard]},
  {path: 'faculties', component: FacultiesPageComponent, canActivate: [AuthGuard]},
  {path: 'faculties/:id', component: SingleFacultyComponent, canActivate: [AuthGuard]},
  {path: '**', redirectTo: ''}
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
