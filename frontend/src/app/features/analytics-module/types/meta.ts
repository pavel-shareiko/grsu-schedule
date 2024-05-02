import {FieldDefinition} from "../../../core/components/form/dynamic-form/types/field-definition";

export class AnalyticsModuleMeta {
  moduleName: string;
  displayName: string;
  input: FieldDefinition[];
  output: FieldDefinition[];

  constructor(moduleName: string, displayName: string, input: FieldDefinition[], output: FieldDefinition[]) {
    this.moduleName = moduleName;
    this.displayName = displayName;
    this.input = input;
    this.output = output;
  }
}
