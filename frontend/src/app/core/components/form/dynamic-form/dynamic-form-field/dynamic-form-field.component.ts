import {Component, Input} from '@angular/core';
import {FormGroup, ReactiveFormsModule} from "@angular/forms";
import {FieldDefinition} from "../types/field-definition";
import {NgForOf, NgIf, NgSwitch, NgSwitchCase} from "@angular/common";
import {MatFormField, MatHint, MatLabel, MatSuffix} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatCheckbox} from "@angular/material/checkbox";
import {MatCalendar, MatDatepicker, MatDatepickerInput, MatDatepickerToggle} from "@angular/material/datepicker";
import {MatIcon} from "@angular/material/icon";
import {MatOption, MatSelect} from "@angular/material/select";
import {
  MultiselectSearchComponent
} from "../../multiselect-search/multiselect-search.component";
import {
  DynamicAutocompleteFormFieldComponent
} from "../dynamic-autocomplete-form-field/dynamic-autocomplete-form-field.component";

@Component({
  selector: 'app-dynamic-form-field',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgSwitch,
    NgSwitchCase,
    NgForOf,
    NgIf,
    MatFormField,
    MatInput,
    MatLabel,
    MatCheckbox,
    MatCalendar,
    MatDatepickerInput,
    MatHint,
    MatDatepickerToggle,
    MatDatepicker,
    MatSuffix,
    MatIcon,
    MatSelect,
    MatOption,
    MultiselectSearchComponent,
    DynamicAutocompleteFormFieldComponent
  ],
  templateUrl: './dynamic-form-field.component.html',
  styleUrl: './dynamic-form-field.component.scss'
})
export class DynamicFormFieldComponent {
  @Input() field!: FieldDefinition;
  @Input() form!: FormGroup;
  get isValid() {
    return this.form.controls[this.field.key].valid;
  }
}
