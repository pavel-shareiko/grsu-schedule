import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SubjectCard} from "../type/subject";

@Injectable({
  providedIn: 'root'
})
export class SubjectCardService {

  constructor(private http: HttpClient) {
  }

  saveSubjectCard(subjectId: number, lessonsSequence: string) {
    return this.http.post<SubjectCard>(
      `/api/v1/subject-cards`, {
        subjectId,
        lessonsSequence
      })
  }

  deleteSubjectCard(subjectId: number) {
    return this.http.delete(
      `/api/v1/subject-cards/${subjectId}`)
  }
}
