import { TestBed } from '@angular/core/testing';

import { ModuleCardService } from './module-card.service';

describe('ModuleCardService', () => {
  let service: ModuleCardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ModuleCardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
