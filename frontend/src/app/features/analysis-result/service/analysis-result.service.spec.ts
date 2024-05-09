import { TestBed } from '@angular/core/testing';

import { AnalysisResultService } from './analysis-result.service';

describe('AnalysisResultService', () => {
  let service: AnalysisResultService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnalysisResultService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
