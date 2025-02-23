import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ButtonFavoriteTipComponent } from './button-favorite-tip.component';

describe('ButtonFavoriteTipComponent', () => {
  let component: ButtonFavoriteTipComponent;
  let fixture: ComponentFixture<ButtonFavoriteTipComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ButtonFavoriteTipComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ButtonFavoriteTipComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
