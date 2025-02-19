import { TestBed } from '@angular/core/testing';

import { AdvServiceService } from './adv.service';

describe('AdvServiceService', () => {
  let service: AdvServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdvServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
