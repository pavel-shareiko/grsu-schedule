import {
  AfterViewInit,
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  Inject,
  OnInit,
  ViewChild
} from "@angular/core";
import {MatButtonModule} from "@angular/material/button";
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogTitle
} from "@angular/material/dialog";
import {
  DynamicFormComponent,
  FormSubmittedEvent
} from "../../../../core/components/form/dynamic-form/dynamic-form.component";
import {DialogRef} from "@angular/cdk/dialog";
import {FormSubmitService} from "../../../../core/components/form/dynamic-form/services/form-submit.service";
import {AnalyticsModuleMetaService} from "../../../analytics-module/services/analytics-module-meta.service";
import {AnalyticsModuleMeta} from "../../../analytics-module/types/meta";

export type AnalysisRerunDialogData = {
  moduleName: string;
  initialState?: any;
  onRunClicked?: (dialog: AnalysisRerunDialog) => void;
}

@Component({
  templateUrl: 'analysis-rerun.component.html',
  styleUrl: 'analysis-rerun.component.scss',
  standalone: true,
  imports: [MatButtonModule, MatDialogActions, MatDialogClose, MatDialogTitle, MatDialogContent, DynamicFormComponent],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AnalysisRerunDialog implements OnInit, AfterViewInit {
  @ViewChild(DynamicFormComponent) dynamicForm!: DynamicFormComponent;
  meta!: AnalyticsModuleMeta;

  constructor(@Inject(MAT_DIALOG_DATA) public data: AnalysisRerunDialogData,
              private dialogRef: DialogRef<AnalysisRerunDialog>,
              private cd: ChangeDetectorRef,
              private analyticsModuleMetaService: AnalyticsModuleMetaService,
              private formSubmitService: FormSubmitService
  ) {
  }

  ngOnInit() {
    this.analyticsModuleMetaService.getModuleMeta(this.data.moduleName).subscribe(meta => {
      this.meta = meta;
      this.cd.detectChanges();
    });
  }

  ngAfterViewInit() {
    this.cd.detectChanges();
  }

  onRunClicked() {
    if (this.dynamicForm.form.valid) {
      const event = new FormSubmittedEvent(this.dynamicForm.formId, this.dynamicForm.form.value, this.dynamicForm.form)
      if (this.data.onRunClicked) {
        this.data.onRunClicked(this);
      }

      this.formSubmitService.formSubmitted(event);
      this.dialogRef.close();
    }
  }
}
