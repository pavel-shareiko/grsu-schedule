import {FieldDefinition} from "../../../core/components/form/dynamic-form/types/field-definition";

export class AnalyticsModuleMeta {
  constructor(public moduleName: string,
              public displayName: string,
              public input: FieldDefinition[],
              public output: FieldDefinition[]) {
  }
}
