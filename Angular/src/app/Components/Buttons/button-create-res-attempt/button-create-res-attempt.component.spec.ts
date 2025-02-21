import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ButtonCreateResAttemptComponent } from './button-create-res-attempt.component';

describe('ButtonCreateResAttemptComponent', () => {
  let component: ButtonCreateResAttemptComponent;
  let fixture: ComponentFixture<ButtonCreateResAttemptComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ButtonCreateResAttemptComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ButtonCreateResAttemptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
