import {Injectable} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {FieldDefinition} from "../types/field-definition";

@Injectable({
  providedIn: 'root'
})
export class DynamicFormServiceService {
  toFormGroup(fields: FieldDefinition[] | null): FormGroup {
    const group: any = {};

    if (!fields) {
      return group;
    }

    fields.forEach(f => {
      group[f.key] = f.required
        ? new FormControl(null, Validators.required)
        : new FormControl(null);
    })

    return new FormGroup(group);
  }
}
