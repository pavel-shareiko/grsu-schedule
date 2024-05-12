import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FacultiesPageComponent } from './faculties-page.component';

describe('FacultiesPageComponent', () => {
  let component: FacultiesPageComponent;
  let fixture: ComponentFixture<FacultiesPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FacultiesPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FacultiesPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
