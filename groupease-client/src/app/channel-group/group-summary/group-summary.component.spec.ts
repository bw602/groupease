import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupSummaryComponent } from './group-summary.component';
import { MatCardModule, MatIconModule, MatListModule } from '@angular/material';
import { Group } from '../../core/group';

describe('GroupSummaryComponent', () => {
  let component: GroupSummaryComponent;
  let fixture: ComponentFixture<GroupSummaryComponent>;
  let group: Group;

  beforeEach(async(() => {

    group = new Group();
    group.id = 123;
    group.name = 'Group Name';
    group.description = 'Group description';
    group.channelId = 88;

    TestBed.configureTestingModule({
      declarations: [
        GroupSummaryComponent
      ],
      imports: [
        MatCardModule,
        MatIconModule,
        MatListModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupSummaryComponent);
    component = fixture.componentInstance;
    component.group = group;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
