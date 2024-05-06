import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {FormSubmittedEvent} from "../dynamic-form.component";

@Injectable({
  providedIn: 'root'
})
export class FormSubmitService {
  private formSubmittedSource = new Subject<FormSubmittedEvent>();

  formSubmitted$ = this.formSubmittedSource.asObservable();

  formSubmitted(event: FormSubmittedEvent) {
    this.formSubmittedSource.next(event);
  }
}
