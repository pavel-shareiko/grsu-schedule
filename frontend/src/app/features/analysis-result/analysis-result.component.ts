import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {AnalysisResultService} from "./service/analysis-result.service";
import {AnalysisResult} from "./types/analysis-result";
import {AnalyticsModuleMeta} from "../analytics-module/types/meta";
import {AnalyticsModuleMetaService} from "../analytics-module/services/analytics-module-meta.service";
import {DatePipe, Location, JsonPipe, NgForOf, NgIf, NgTemplateOutlet} from "@angular/common";
import {ResourceDescription} from "../../core/components/form/dynamic-form/types/resource-description";
import {FieldDefinition} from "../../core/components/form/dynamic-form/types/field-definition";
import {HttpClient} from "@angular/common/http";
import {SearchDescriptor} from "../../core/components/form/multiselect-search/multiselect-search.component";
import {MatCell} from "@angular/material/table";
import {MatIcon} from "@angular/material/icon";
import {MatIconButton} from "@angular/material/button";

@Component({
  selector: 'app-analysis-result',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    JsonPipe,
    MatCell,
    DatePipe,
    RouterLink,
    NgTemplateOutlet,
    MatIcon,
    MatIconButton
  ],
  templateUrl: './analysis-result.component.html',
  styleUrl: './analysis-result.component.scss'
})
export class AnalysisResultComponent implements OnInit {
  analysisResult!: AnalysisResult;
  meta!: AnalyticsModuleMeta;
  referenceDisplayNames: { [key: string]: SearchDescriptor } = {};

  private readonly canGoBack: boolean;

  constructor(private route: ActivatedRoute,
              private analysisResultService: AnalysisResultService,
              private metaService: AnalyticsModuleMetaService,
              private http: HttpClient,
              private router: Router,
              private location: Location
  ) {
    this.canGoBack = !!(this.router.getCurrentNavigation()?.previousNavigation);
  }

  goBack() {
    if (this.canGoBack) {
      this.location.back();
    } else {
      this.router.navigate(['..'], {relativeTo: this.route});
    }
  }

  ngOnInit(): void {
    const resultId = parseInt(this.route.snapshot.paramMap.get('id')!);
    if (Number.isNaN(resultId)) {
      throw new Error('Invalid result id');
    }
    this.analysisResultService.findResultById(resultId).subscribe(result => {
      this.analysisResult = AnalysisResult.fromJson(result.payload[0]);
      this.metaService.getModuleMeta(this.analysisResult.moduleName).subscribe(meta => {
        this.meta = meta;

        this.meta.input.forEach(field => {
          if (field.type.startsWith('reference$$')) {
            const identifierValue = this.analysisResult.context[field.key];
            this.fetchReferenceDisplayName(field, identifierValue);
          }
        })
        this.meta.output.forEach(field => {
          if (field.type.startsWith('reference$$')) {
            const identifierValue = this.analysisResult.result[field.key];
            this.fetchReferenceDisplayName(field, identifierValue);
          }
        })
      });
    });
  }

  getReferenceDisplayValue(referenceField: FieldDefinition): string {
    return this.referenceDisplayNames[referenceField.key]?.displayValue;
  }

  fetchReferenceDisplayName(field: FieldDefinition, identifierValue: any) {
    const resourceDescription = ResourceDescription.fromString(field.type);

    this.http.post(resourceDescription.url, {
      [resourceDescription.identifier]: identifierValue
    }).subscribe((response: any) => {
      const content = response[resourceDescription.contentPath];
      const searchResult = content[0];
      this.referenceDisplayNames[field.key] = {
        source: searchResult,
        value: identifierValue,
        displayValue: this.formatValue(resourceDescription.displayFormat, searchResult)
      }
    });
  }

  protected formatValue(displayFormat: string, elem: any) {
    if (!elem) {
      return '';
    }
    return displayFormat.replace(/{(.*?)}/g, (match, key) => elem[key]);
  }
}
