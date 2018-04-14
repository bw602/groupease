import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupMemberListComponent } from './group-member-list.component';
import { MatExpansionModule, MatIconModule, MatListModule } from '@angular/material';
import { Member } from '../../core/member';

describe('GroupMemberListComponent', () => {
  let component: GroupMemberListComponent;
  let fixture: ComponentFixture<GroupMemberListComponent>;
  let members: Member[];

  beforeEach(async(() => {

    members = [];

    TestBed.configureTestingModule({
      declarations: [
        GroupMemberListComponent
      ],
      imports: [
        MatExpansionModule,
        MatIconModule,
        MatListModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupMemberListComponent);
    component = fixture.componentInstance;
    component.members = members;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
