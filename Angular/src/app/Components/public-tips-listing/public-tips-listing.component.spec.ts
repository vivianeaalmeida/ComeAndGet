import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicTipsListingComponent } from './public-tips-listing.component';

describe('PublicTipsListingComponent', () => {
  let component: PublicTipsListingComponent;
  let fixture: ComponentFixture<PublicTipsListingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PublicTipsListingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PublicTipsListingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
