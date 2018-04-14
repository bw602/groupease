import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupDetailsHomeComponent } from './group-details-home.component';

describe('GroupDetailsHomeComponent', () => {
  let component: GroupDetailsHomeComponent;
  let fixture: ComponentFixture<GroupDetailsHomeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupDetailsHomeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupDetailsHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
