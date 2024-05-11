import {Component, Input, TemplateRef} from '@angular/core';
import {ShortAnalyticsModuleInfo} from "../../../core/models/analytics-module";
import {strings as russianStrings} from "ngx-timeago/language-strings/ru";
import {TimeagoIntl, TimeagoModule} from "ngx-timeago";
import {RouterLink} from "@angular/router";
import {NgTemplateOutlet} from "@angular/common";

@Component({
  selector: 'app-module-card',
  standalone: true,
  imports: [
    TimeagoModule,
    RouterLink,
    NgTemplateOutlet
  ],
  templateUrl: './module-card.component.html',
  styleUrl: './module-card.component.scss'
})
export class ModuleCardComponent {
  @Input({required: true}) module!: ShortAnalyticsModuleInfo;
  @Input({required: true}) redirectUrl!: string;
  @Input() button!: TemplateRef<any>;

  constructor(intl: TimeagoIntl) {
    intl.strings = russianStrings;
    intl.changes.next();
  }
}
