import { TestBed } from '@angular/core/testing';

import { AnalyticsModuleMetaService } from './analytics-module-meta.service';

describe('AnalyticsModuleMetaService', () => {
  let service: AnalyticsModuleMetaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnalyticsModuleMetaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
