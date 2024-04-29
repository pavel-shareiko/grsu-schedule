import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModuleCardGridComponent } from './module-card-grid.component';

describe('ModuleCardGridComponent', () => {
  let component: ModuleCardGridComponent;
  let fixture: ComponentFixture<ModuleCardGridComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModuleCardGridComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ModuleCardGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
