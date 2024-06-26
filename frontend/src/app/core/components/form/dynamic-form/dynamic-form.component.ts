import {Component, EventEmitter, Input, OnInit, Output, TemplateRef} from '@angular/core';
import {FormGroup, ReactiveFormsModule} from "@angular/forms";
import {FieldDefinition} from "./types/field-definition";
import {DynamicFormServiceService} from "./services/dynamic-form-service.service";
import {NgForOf, NgIf, NgTemplateOutlet} from "@angular/common";
import {DynamicFormFieldComponent} from "./dynamic-form-field/dynamic-form-field.component";
import {MatButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatListItem} from "@angular/material/list";
import {MatCard, MatCardContent} from "@angular/material/card";
import {MatOption} from "@angular/material/autocomplete";

export class FormSubmittedEvent {
  constructor(public key: string, public value: any, public form: FormGroup) {
  }
}

@Component({
  selector: 'app-dynamic-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgForOf,
    DynamicFormFieldComponent,
    NgIf,
    MatButton,
    MatIcon,
    MatListItem,
    MatCard,
    MatCardContent,
    MatOption,
    NgTemplateOutlet
  ],
  templateUrl: './dynamic-form.component.html',
  styleUrl: './dynamic-form.component.scss'
})
export class DynamicFormComponent implements OnInit {
  @Input() fields: FieldDefinition[] | null = [];
  @Input() submitButtonTemplate!: TemplateRef<any> | undefined;
  @Input() initialState!: any;
  @Input() formId: string = "dynamicForm";

  @Output()
  submitEvent = new EventEmitter<FormSubmittedEvent>();

  form!: FormGroup;

  constructor(private dfs: DynamicFormServiceService) {
  }

  ngOnInit() {
    this.form = this.dfs.toFormGroup(this.fields, this.initialState);
  }

  onSubmit() {
    this.submitEvent.emit(new FormSubmittedEvent(this.formId, this.form.value, this.form));
  }
}
