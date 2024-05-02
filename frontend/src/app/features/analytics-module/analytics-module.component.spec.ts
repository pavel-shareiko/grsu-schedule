import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnalyticsModuleComponent } from './analytics-module.component';

describe('AnalyticsModuleComponent', () => {
  let component: AnalyticsModuleComponent;
  let fixture: ComponentFixture<AnalyticsModuleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnalyticsModuleComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AnalyticsModuleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
