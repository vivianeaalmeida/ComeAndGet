import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ButtonDeactivateAdvertisementComponent } from './button-deactivate-advertisement.component';

describe('ButtonDeactivateAdvertisementComponent', () => {
  let component: ButtonDeactivateAdvertisementComponent;
  let fixture: ComponentFixture<ButtonDeactivateAdvertisementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ButtonDeactivateAdvertisementComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ButtonDeactivateAdvertisementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
