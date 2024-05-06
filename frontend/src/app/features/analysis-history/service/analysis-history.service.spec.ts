import { TestBed } from '@angular/core/testing';

import { AnalysisHistoryService } from './analysis-history.service';

describe('AnalysisHistoryService', () => {
  let service: AnalysisHistoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnalysisHistoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
