import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategorieslistingComponent } from './categorieslisting.component';

describe('CategorieslistingComponent', () => {
  let component: CategorieslistingComponent;
  let fixture: ComponentFixture<CategorieslistingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CategorieslistingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CategorieslistingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
