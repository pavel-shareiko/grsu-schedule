export type ShortAnalyticsModuleInfo = {
  displayName: string;
  systemName: string;
  usesCount: number;
  description: string;
  scope: ModuleScope[];
  latestResult: ShortAnalysisHistory;
}

export enum ModuleScope {
  TEACHER = 'Учитель',
  GROUP = 'Группа',
  SUBJECT = 'Предмет',
  FACULTY = 'Факультет'
}

export type ShortAnalysisHistory = {
  moduleName: string;
  status: AnalysisStatus;
  timestamp: string
}

export enum AnalysisStatus {
  SUCCESS = 'Успешно',
  ERROR = 'Ошибка'
}
