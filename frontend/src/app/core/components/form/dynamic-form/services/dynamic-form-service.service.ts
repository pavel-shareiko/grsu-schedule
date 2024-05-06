import {Injectable} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {FieldDefinition} from "../types/field-definition";

@Injectable({
  providedIn: 'root'
})
export class DynamicFormServiceService {
  toFormGroup(fields: FieldDefinition[] | null, initialState: any = undefined): FormGroup {
    const group: any = {};

    if (!fields) {
      return group;
    }

    fields.forEach(f => {
      let initialValue = null;

      if (initialState && initialState[f.key]) {
        initialValue = initialState[f.key];
      }

      group[f.key] = f.required
        ? new FormControl(initialValue, Validators.required)
        : new FormControl(initialValue);
    })

    return new FormGroup(group);
  }
}
