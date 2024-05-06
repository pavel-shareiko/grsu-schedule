import { TestBed } from '@angular/core/testing';

import { AnalyticsModuleService } from './analytics-module.service';

describe('ModuleCardService', () => {
  let service: AnalyticsModuleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnalyticsModuleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
