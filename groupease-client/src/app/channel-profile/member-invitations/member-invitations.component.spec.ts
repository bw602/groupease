import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MemberInvitationsComponent } from './member-invitations.component';

describe('MemberInvitationsComponent', () => {
  let component: MemberInvitationsComponent;
  let fixture: ComponentFixture<MemberInvitationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MemberInvitationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MemberInvitationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
