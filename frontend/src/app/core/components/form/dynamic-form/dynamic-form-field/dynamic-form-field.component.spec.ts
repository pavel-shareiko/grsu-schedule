import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DynamicFormFieldComponent } from './dynamic-form-field.component';

describe('FormFieldComponent', () => {
  let component: DynamicFormFieldComponent;
  let fixture: ComponentFixture<DynamicFormFieldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DynamicFormFieldComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DynamicFormFieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
