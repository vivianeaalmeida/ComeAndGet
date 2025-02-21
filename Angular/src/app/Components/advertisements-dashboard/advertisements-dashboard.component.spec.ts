import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvertisementsDashboardComponent } from './advertisements-dashboard.component';

describe('AdvertisementsDashboardComponent', () => {
  let component: AdvertisementsDashboardComponent;
  let fixture: ComponentFixture<AdvertisementsDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdvertisementsDashboardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdvertisementsDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
