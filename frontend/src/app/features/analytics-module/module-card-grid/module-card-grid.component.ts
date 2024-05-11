import {Component, Input, OnInit, TemplateRef} from '@angular/core';
import {ModuleScope, ShortAnalyticsModuleInfo} from "../../../core/models/analytics-module";
import {ModuleCardComponent} from "../module-card/module-card.component";
import {NgForOf} from "@angular/common";
import {KebabCasePipe} from "../../../core/pipes/kebab-case.pipe";
import {AnalyticsModuleService} from "../services/analytics-module.service";

@Component({
  selector: 'app-module-card-grid',
  standalone: true,
  imports: [
    ModuleCardComponent,
    NgForOf,
    KebabCasePipe
  ],
  templateUrl: './module-card-grid.component.html',
  styleUrl: './module-card-grid.component.scss'
})
export class ModuleCardGridComponent implements OnInit {
  @Input() modules: ShortAnalyticsModuleInfo[] | undefined;
  @Input() scope: ModuleScope[] = []
  @Input() initialValue!: any;
  @Input() button!: TemplateRef<any>;

  constructor(private analyticsModuleService: AnalyticsModuleService) {
  }

  ngOnInit(): void {
    if (this.modules) {
      return;
    }

    if (this.scope) {
      this.analyticsModuleService.getModulesWithScope(this.scope).subscribe({
        next: response => {
          this.modules = response.modules;
        }
      });
    }
  }
}
