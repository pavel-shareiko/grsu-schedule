import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubjectsListComponent } from './subjects-list.component';

describe('SubjectsListComponent', () => {
  let component: SubjectsListComponent;
  let fixture: ComponentFixture<SubjectsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubjectsListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SubjectsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
