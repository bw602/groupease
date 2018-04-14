import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupDetailsRequestsComponent } from './group-details-requests.component';

describe('GroupDetailsRequestsComponent', () => {
  let component: GroupDetailsRequestsComponent;
  let fixture: ComponentFixture<GroupDetailsRequestsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupDetailsRequestsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupDetailsRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
