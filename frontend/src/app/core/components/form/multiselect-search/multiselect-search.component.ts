import {
  ChangeDetectorRef,
  Component,
  DestroyRef,
  ElementRef,
  EventEmitter,
  forwardRef,
  Input,
  Output,
  TemplateRef,
  ViewChild
} from '@angular/core';
import {
  ControlContainer,
  FormControl,
  FormGroup,
  FormsModule,
  NG_VALUE_ACCESSOR,
  ReactiveFormsModule
} from "@angular/forms";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {
  MatChip,
  MatChipGrid,
  MatChipInput,
  MatChipInputEvent,
  MatChipOption,
  MatChipRemove,
  MatChipRow
} from "@angular/material/chips";
import {MatIcon} from "@angular/material/icon";
import {
  MatAutocomplete,
  MatAutocompleteSelectedEvent,
  MatAutocompleteTrigger,
  MatOption
} from "@angular/material/autocomplete";
import {NgForOf, NgIf, NgTemplateOutlet} from "@angular/common";
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {debounceTime, distinctUntilChanged} from "rxjs";
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";
import {FieldDefinition} from "../dynamic-form/types/field-definition";
import {MatProgressSpinner} from "@angular/material/progress-spinner";

export interface SearchDescriptor {
  source: any;
  value: string;
  displayValue: string;
}

@Component({
  selector: 'app-multiselect-search',
  standalone: true,
  imports: [
    MatFormField,
    FormsModule,
    MatChip,
    MatIcon,
    MatAutocompleteTrigger,
    MatChipInput,
    ReactiveFormsModule,
    MatAutocomplete,
    MatOption,
    NgTemplateOutlet,
    NgForOf,
    NgIf,
    MatProgressSpinner,
    MatChipGrid,
    MatChipOption,
    MatLabel,
    MatChipRemove,
    MatChipRow
  ],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => MultiselectSearchComponent),
      multi: true
    }
  ],
  templateUrl: './multiselect-search.component.html',
  styleUrl: './multiselect-search.component.scss'
})
export class MultiselectSearchComponent {
  public readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  public inputControl!: FormControl;
  public returnValues: string[] = [];
  public displayValues: string[] = [];

  @ViewChild('input', {static: false})
  public inputElement!: ElementRef | null;

  @Input()
  public label!: string;
  @Input()
  public required = false;
  @Input()
  public disabled = false;
  @Input()
  public loading = false;
  @Input()
  public optionTemplate!: TemplateRef<any>;
  @Input()
  public searchResults: SearchDescriptor[] = [];
  @Input()
  public multi: boolean = true;
  @Input({required: true}) field!: FieldDefinition;
  @Input({required: true}) form!: FormGroup;

  @Output()
  public search = new EventEmitter<string>();

  constructor(
    private cdr: ChangeDetectorRef,
    private destroy: DestroyRef,
  ) {
  }

  public onChange = (_: any) => {
  };
  public onTouched = () => {
  };

  ngOnInit() {
    this.inputControl = this.form.get(this.field.key) as FormControl;
    this.required = this.field.required;
    this.emitSearchOnTyping();

    this.inputControl.valueChanges.subscribe(value => {
      if (value === null) {
        this.displayValues = [];
        this.returnValues = [];
      }
    })
  }

  writeValue(value: string[]): void {
    this.returnValues = value;
    this.displayValues = value;
    this.cdr.markForCheck();
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
    this.cdr.markForCheck();
  }

  /**
   * Event handler for removing an item from the list of selected.
   * @param item Removed element.
   */
  public onRemoveItem(item: string): void {
    const index = this.displayValues.indexOf(item);

    if (index >= 0) {
      this.removeValue(index);
    }
  }

  /**
   * Event handler for adding an item to the list of selected.
   * @param event The object of the confirmation event of the entered word.
   */
  public onManuallyAddItem(event: MatChipInputEvent): void {
    const newValue = event.value.trim().replace(/ /g, '');
    const displayValue = newValue + ' (добавлено вручную)';

    if (!this.returnValues.includes(newValue) && newValue) {
      this.addValue(newValue, displayValue);
    }

    event.chipInput.inputElement.value = '';
    this.searchResults = [];
  }

  /**
   * Event handler for adding an item from search to the list of selected.
   * @param event The event object for selecting an item from the list of proposed.
   */
  public onSelectOptionFromSearchResult(event: MatAutocompleteSelectedEvent): void {
    this.addValue(event.option.value.value, event.option.value.displayValue);
    if (this.inputElement) {
      this.inputElement.nativeElement.value = '';
    }
  }

  private removeValue(index: number) {
    this.returnValues.splice(index, 1);
    this.displayValues.splice(index, 1);
    this.updateControlValue();
  }

  private addValue(returnValue: string, displayValue: string) {
    if (this.multi) {
      this.returnValues.push(returnValue);
      this.displayValues.push(displayValue);
    } else {
      this.returnValues = [returnValue];
      this.displayValues = [displayValue];
    }
    this.updateControlValue();
  }

  private updateControlValue() {
    if (this.multi) {
      this.inputControl.setValue(this.returnValues, {emitEvent: false});
    } else {
      this.inputControl.setValue(this.returnValues[0], {emitEvent: false});
    }
  }

  /** Sends a search event when you enter a search word. */
  private emitSearchOnTyping(): void {
    this.inputControl
      .valueChanges
      .pipe(
        takeUntilDestroyed(this.destroy),
        debounceTime(300),
        distinctUntilChanged(),
      )
      .subscribe(value => {
        if (typeof value === 'string') {
          this.search.emit(value);
        }
      });
  }
}

