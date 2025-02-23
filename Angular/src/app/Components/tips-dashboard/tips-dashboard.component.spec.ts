import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TipsDashboardComponent } from './tips-dashboard.component';

describe('TipsDashboardComponent', () => {
  let component: TipsDashboardComponent;
  let fixture: ComponentFixture<TipsDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TipsDashboardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TipsDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
