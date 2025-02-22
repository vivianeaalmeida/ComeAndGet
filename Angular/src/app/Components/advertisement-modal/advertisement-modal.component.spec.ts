import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvertisementModalComponent } from './advertisement-modal.component';

describe('AdvertisementModalComponent', () => {
  let component: AdvertisementModalComponent;
  let fixture: ComponentFixture<AdvertisementModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdvertisementModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdvertisementModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
