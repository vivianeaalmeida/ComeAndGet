import { TestBed } from '@angular/core/testing';

import { ResAttemptService } from './res-attempt.service';

describe('ResAttemptService', () => {
  let service: ResAttemptService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ResAttemptService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
