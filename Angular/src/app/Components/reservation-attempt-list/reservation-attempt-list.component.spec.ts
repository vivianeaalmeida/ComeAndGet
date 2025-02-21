import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationAttemptListComponent } from './reservation-attempt-list.component';

describe('ReservationAttemptListComponent', () => {
  let component: ReservationAttemptListComponent;
  let fixture: ComponentFixture<ReservationAttemptListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReservationAttemptListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ReservationAttemptListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
