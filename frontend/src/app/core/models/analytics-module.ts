export type ShortAnalyticsModuleInfo = {
  displayName: string;
  systemName: string;
  usesCount: number;
  description: string;
  scope: ModuleScope[];
  latestResult: ShortAnalysisHistory;
}

export enum ModuleScope {
  TEACHER = <any>'Учитель',
  GROUP = <any>'Группа',
  SUBJECT = <any>'Предмет',
  FACULTY = <any>'Факультет'
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
