import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupDetailsInvitationsComponent } from './group-details-invitations.component';

describe('GroupDetailsInvitationsComponent', () => {
  let component: GroupDetailsInvitationsComponent;
  let fixture: ComponentFixture<GroupDetailsInvitationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupDetailsInvitationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupDetailsInvitationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
