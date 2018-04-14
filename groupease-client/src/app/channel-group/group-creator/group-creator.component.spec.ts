import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupCreatorComponent } from './group-creator.component';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { MatButtonModule, MatCardModule, MatFormFieldModule, MatInputModule, MatSnackBarModule } from '@angular/material';
import { GroupService } from '../../core/group.service';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { Channel } from '../../core/channel';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('GroupCreatorComponent', () => {
  let component: GroupCreatorComponent;
  let fixture: ComponentFixture<GroupCreatorComponent>;
  let groupService: jasmine.SpyObj<GroupService>;
  let channel: Channel;

  beforeEach(async(() => {

    channel = new Channel();
    channel.id = 123;
    channel.name = 'channel name';
    channel.description = 'channel description';
    channel.lastUpdatedOn = 1523198394721;

    groupService = jasmine.createSpyObj(
      'GroupService',
      [
        'createGroup'
      ]
    );

    TestBed.configureTestingModule({
      declarations: [
        GroupCreatorComponent
      ],
      providers: [
        {
          provide: GroupService,
          useValue: groupService
        },
        {
          provide: ActivatedRoute,
          useValue: {
            parent: {
              parent: {
                data: Observable.of(
                  {
                    channel: channel
                  }
                )
              }
            }
          }
        }
      ],
      imports: [
        NoopAnimationsModule,
        FormsModule,
        MatButtonModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule,
        MatSnackBarModule,
        RouterTestingModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupCreatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
