import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FavoriteTipDetailComponent } from './favorite-tip-detail.component';

describe('FavoriteTipDetailComponent', () => {
  let component: FavoriteTipDetailComponent;
  let fixture: ComponentFixture<FavoriteTipDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FavoriteTipDetailComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FavoriteTipDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
