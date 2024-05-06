import { TestBed } from '@angular/core/testing';

import { FormSubmitService } from './form-submit.service';

describe('FormSubmitService', () => {
  let service: FormSubmitService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FormSubmitService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
