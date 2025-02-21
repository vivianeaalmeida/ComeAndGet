import { TestBed } from '@angular/core/testing';

import { ReservationAttemptService } from './reservation-attempt.service';

describe('ReservationAttemptService', () => {
  let service: ReservationAttemptService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReservationAttemptService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
