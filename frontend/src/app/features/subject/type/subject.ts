export class Subject {
  constructor(
    public id: number,
    public title: string,
    public createTimestamp: string,
    public subjectCard: SubjectCard
  ) {
  }
}

export class SubjectCard {
  constructor(
    public id: number,
    public subjectId: number,
    public lessonsSequence: string
  ) {
  }
}

export interface SubjectSearchFilter {
  id?: number;
  title?: string;
  isSubjectCardPresent?: boolean;
}
