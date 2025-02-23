import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ButtonLikeTipComponent } from './button-like-tip.component';

describe('ButtonLikeTipComponent', () => {
  let component: ButtonLikeTipComponent;
  let fixture: ComponentFixture<ButtonLikeTipComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ButtonLikeTipComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ButtonLikeTipComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
