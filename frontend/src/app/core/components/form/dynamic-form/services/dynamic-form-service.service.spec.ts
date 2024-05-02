import { TestBed } from '@angular/core/testing';

import { DynamicFormServiceService } from './dynamic-form-service.service';

describe('DynamicFormServiceService', () => {
  let service: DynamicFormServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DynamicFormServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
