import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogClose} from "@angular/material/dialog";
import {MatIcon} from "@angular/material/icon";

class ConfirmationDialogData {
  constructor(public title?: string,
              public message?: string) {
  }
}

@Component({
  selector: 'app-confirmation-dialog',
  standalone: true,
  imports: [
    MatIcon,
    MatDialogClose
  ],
  templateUrl: './confirmation-dialog.component.html',
  styleUrl: './confirmation-dialog.component.scss'
})
export class ConfirmationDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: ConfirmationDialogData) {
  }

}
