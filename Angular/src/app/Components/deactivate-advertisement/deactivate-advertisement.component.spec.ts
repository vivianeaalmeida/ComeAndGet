import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeactivateAdvertisementComponent } from './deactivate-advertisement.component';

describe('DeactivateAdvertisementComponent', () => {
  let component: DeactivateAdvertisementComponent;
  let fixture: ComponentFixture<DeactivateAdvertisementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeactivateAdvertisementComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DeactivateAdvertisementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
