import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvlistingComponent } from './advlisting.component';

describe('AdvlistingComponent', () => {
  let component: AdvlistingComponent;
  let fixture: ComponentFixture<AdvlistingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdvlistingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdvlistingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
