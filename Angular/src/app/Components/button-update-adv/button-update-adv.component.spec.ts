import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ButtonUpdateAdvComponent } from './button-update-adv.component';

describe('ButtonUpdateAdvComponent', () => {
  let component: ButtonUpdateAdvComponent;
  let fixture: ComponentFixture<ButtonUpdateAdvComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ButtonUpdateAdvComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ButtonUpdateAdvComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
