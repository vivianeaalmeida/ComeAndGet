import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateAdvModalComponent } from './update-adv-modal.component';

describe('UpdateAdvModalComponent', () => {
  let component: UpdateAdvModalComponent;
  let fixture: ComponentFixture<UpdateAdvModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpdateAdvModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UpdateAdvModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
