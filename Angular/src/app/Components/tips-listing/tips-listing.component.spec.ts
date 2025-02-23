import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TipsListingComponent } from './tips-listing.component';

describe('TipsListingComponent', () => {
  let component: TipsListingComponent;
  let fixture: ComponentFixture<TipsListingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TipsListingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TipsListingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
