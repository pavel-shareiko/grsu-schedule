import {Component} from '@angular/core';
import {ModuleCardComponent} from "../analytics-module/module-card/module-card.component";
import {AnalysisStatus, ModuleScope, ShortAnalyticsModuleInfo} from "../../core/models/analytics-module";
import {ModuleCardGridComponent} from "../analytics-module/module-card-grid/module-card-grid.component";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    ModuleCardComponent,
    ModuleCardGridComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  modules: ShortAnalyticsModuleInfo[] = [
    {
      displayName: 'Анализ перемещений',
      description: 'Модуль для анализа перемещений между корпусами',
      scope: [ModuleScope.GROUP, ModuleScope.TEACHER],
      usesCount: 7,
      systemName: 'rotationsAnalyticsModule',
      latestResult: {
        moduleName: 'rotationsAnalyticsModule',
        status: AnalysisStatus.SUCCESS,
        timestamp: '2024-01-01T00:00:00'
      }
    },
    {
      displayName: 'abc',
      systemName: 'abc',
      usesCount: 4,
      description: 'description',
      scope: [],
      latestResult: {
        moduleName: 'abc',
        status: AnalysisStatus.SUCCESS,
        timestamp: '2024-01-01T00:00:00'
      }
    },
    {
      displayName: 'abc',
      systemName: 'abc',
      usesCount: 4,
      description: 'description',
      scope: [],
      latestResult: {
        moduleName: 'abc',
        status: AnalysisStatus.SUCCESS,
        timestamp: '2024-01-01T00:00:00'
      }
    },
    {
      displayName: 'Анализ соответствия расписания методическим картам',
      systemName: 'subjectCardAnalyticsModule',
      usesCount: 7,
      description: 'Модуль для анализа соответствия актуального расписания заявленному',
      scope: [ModuleScope.SUBJECT, ModuleScope.TEACHER],
      latestResult: {
        moduleName: 'subjectCardAnalyticsModule',
        status: AnalysisStatus.SUCCESS,
        timestamp: '2024-01-01T00:00:00'
      }
    },
    {
      displayName: 'Анализ соответствия расписания методическим картам',
      systemName: 'subjectCardAnalyticsModule',
      usesCount: 7,
      description: 'Модуль для анализа соответствия актуального расписания заявленному',
      scope: [ModuleScope.SUBJECT, ModuleScope.TEACHER],
      latestResult: {
        moduleName: 'subjectCardAnalyticsModule',
        status: AnalysisStatus.SUCCESS,
        timestamp: '2024-01-01T00:00:00'
      }
    },
    {
      displayName: 'Анализ соответствия расписания методическим картам',
      systemName: 'subjectCardAnalyticsModule',
      usesCount: 7,
      description: 'Модуль для анализа соответствия актуального расписания заявленному',
      scope: [ModuleScope.SUBJECT, ModuleScope.TEACHER],
      latestResult: {
        moduleName: 'subjectCardAnalyticsModule',
        status: AnalysisStatus.SUCCESS,
        timestamp: '2024-01-01T00:00:00'
      }
    },
    {
      displayName: 'Анализ соответствия расписания методическим картам',
      systemName: 'subjectCardAnalyticsModule',
      usesCount: 7,
      description: 'Модуль для анализа соответствия актуального расписания заявленному',
      scope: [ModuleScope.SUBJECT, ModuleScope.TEACHER],
      latestResult: {
        moduleName: 'subjectCardAnalyticsModule',
        status: AnalysisStatus.SUCCESS,
        timestamp: '2024-01-01T00:00:00'
      }
    },
    {
      displayName: 'Анализ соответствия расписания методическим картам',
      systemName: 'subjectCardAnalyticsModule',
      usesCount: 7,
      description: 'Модуль для анализа соответствия актуального расписания заявленному',
      scope: [ModuleScope.SUBJECT, ModuleScope.TEACHER],
      latestResult: {
        moduleName: 'subjectCardAnalyticsModule',
        status: AnalysisStatus.SUCCESS,
        timestamp: '2024-01-01T00:00:00'
      }
    },
    {
      displayName: 'Анализ соответствия расписания методическим картам',
      systemName: 'subjectCardAnalyticsModule',
      usesCount: 7,
      description: 'Модуль для анализа соответствия актуального расписания заявленному',
      scope: [ModuleScope.SUBJECT, ModuleScope.TEACHER],
      latestResult: {
        moduleName: 'subjectCardAnalyticsModule',
        status: AnalysisStatus.SUCCESS,
        timestamp: '2024-01-01T00:00:00'
      }
    }
  ];
}
