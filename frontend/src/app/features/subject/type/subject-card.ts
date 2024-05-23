const lessonsMap: { [key: string]: string } = {
  'л': 'Лекция (УСР)',
  'Л': 'Лекция',
  'б': 'Лабораторная работа (УСР)',
  'Б': 'Лабораторная работа',
  'п': 'Практика (УСР)',
  'П': 'Практика',
}

export class SubjectCardTranslator {
  constructor(private lessonsSequence?: string) {
  }

  translate() {
    if (!this.lessonsSequence) {
      return '';
    }
    return this.lessonsSequence.split('').map((lesson) => lessonsMap[lesson]).join(' - ');
  }
}
