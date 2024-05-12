import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SingleFacultyComponent } from './single-faculty.component';

describe('SingleFacultyComponent', () => {
  let component: SingleFacultyComponent;
  let fixture: ComponentFixture<SingleFacultyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SingleFacultyComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SingleFacultyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
