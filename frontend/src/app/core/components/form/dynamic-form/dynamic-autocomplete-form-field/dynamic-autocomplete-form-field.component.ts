import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {MultiselectSearchComponent, SearchDescriptor} from "../../multiselect-search/multiselect-search.component";
import {BehaviorSubject} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {AsyncPipe} from "@angular/common";
import {ResourceDescription} from "../types/resource-description";
import {FieldDefinition} from "../types/field-definition";
import {FormGroup, ReactiveFormsModule} from "@angular/forms";

@Component({
  selector: 'app-dynamic-autocomplete-form-field',
  standalone: true,
  imports: [
    MultiselectSearchComponent,
    AsyncPipe,
    ReactiveFormsModule
  ],
  templateUrl: './dynamic-autocomplete-form-field.component.html',
  styleUrl: './dynamic-autocomplete-form-field.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DynamicAutocompleteFormFieldComponent {
  private _searchResults$ = new BehaviorSubject<SearchDescriptor[]>([]);
  public searchResults$ = this._searchResults$.asObservable();

  private _loading$ = new BehaviorSubject<boolean>(false);
  public loading$ = this._loading$.asObservable();

  @Input({required: true, transform: (val: string) => ResourceDescription.fromString(val)})
  resourceDescription!: ResourceDescription;
  @Input({required: true})
  field!: FieldDefinition;
  @Input({required: true})
  form!: FormGroup;

  constructor(private http: HttpClient) {
  }

  /**
   * Search execution event handler.
   * @param value Search value.
   */
  public onSearchSystems(value: string): void {
    if (value.length < 2) {
      this._searchResults$.next([]);
      return;
    }

    this._loading$.next(true);

    this.http.post(this.resourceDescription.url, {
      [this.resourceDescription.paramName]: value
    }).subscribe((response: any) => {
      this._loading$.next(false);

      // assuming content is iterable
      const content = response[this.resourceDescription.contentPath];
      const searchResults: SearchDescriptor[] = content.map((elem: any) => ({
        source: elem,
        value: elem[this.resourceDescription.identifier],
        displayValue: this.formatValue(this.resourceDescription.displayFormat, elem)
      }));

      this._searchResults$.next(searchResults);
    });
  }

  protected formatValue(displayFormat: string, elem: any) {
    return displayFormat.replace(/{(.*?)}/g, (match, key) => elem[key]);
  }
}
