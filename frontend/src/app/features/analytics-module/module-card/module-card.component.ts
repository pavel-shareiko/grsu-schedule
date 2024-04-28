import {Component, Input} from '@angular/core';
import {ShortAnalyticsModuleInfo} from "../../../core/models/analytics-module";
import {strings as russianStrings} from "ngx-timeago/language-strings/ru";
import {TimeagoIntl, TimeagoModule} from "ngx-timeago";

@Component({
  selector: 'app-module-card',
  standalone: true,
  imports: [
    TimeagoModule
  ],
  templateUrl: './module-card.component.html',
  styleUrl: './module-card.component.scss'
})
export class ModuleCardComponent {
  @Input({required: true}) module!: ShortAnalyticsModuleInfo;
  @Input({required: true}) redirectUrl!: string;

  constructor(intl: TimeagoIntl) {
    intl.strings = russianStrings;
    intl.changes.next();
  }

  performDetailsAction() {
    window.open(this.redirectUrl);
  }
}
