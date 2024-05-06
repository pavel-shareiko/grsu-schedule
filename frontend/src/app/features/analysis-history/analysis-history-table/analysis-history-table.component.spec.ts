import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnalysisHistoryTableComponent } from './analysis-history-table.component';

describe('AnalysisHistoryTableComponent', () => {
  let component: AnalysisHistoryTableComponent;
  let fixture: ComponentFixture<AnalysisHistoryTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnalysisHistoryTableComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AnalysisHistoryTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
