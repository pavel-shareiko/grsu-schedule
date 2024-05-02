import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormGroup, ReactiveFormsModule} from "@angular/forms";
import {FieldDefinition} from "./types/field-definition";
import {DynamicFormServiceService} from "./services/dynamic-form-service.service";
import {NgForOf, NgIf} from "@angular/common";
import {DynamicFormFieldComponent} from "./dynamic-form-field/dynamic-form-field.component";
import {MatButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatListItem} from "@angular/material/list";
import {MatCard, MatCardContent} from "@angular/material/card";

export class FormSubmittedEvent {
  constructor(public value: any, public form: FormGroup) {
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
    MatCardContent
  ],
  templateUrl: './dynamic-form.component.html',
  styleUrl: './dynamic-form.component.scss'
})
export class DynamicFormComponent implements OnInit {
  @Input() fields: FieldDefinition[] | null = [];
  @Input({required: true}) submitButtonText!: string;
  @Input() submitButtonIcon!: string;
  @Output() submitEvent = new EventEmitter<FormSubmittedEvent>();
  form!: FormGroup;

  constructor(private dfs: DynamicFormServiceService) {}

  ngOnInit() {
    this.form = this.dfs.toFormGroup(this.fields);
  }

  onSubmit() {
    this.submitEvent.emit(new FormSubmittedEvent(this.form.value, this.form));
  }
}
