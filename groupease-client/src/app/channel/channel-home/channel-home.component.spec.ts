import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChannelHomeComponent } from './channel-home.component';
import { MatCardModule } from '@angular/material';
import { Channel } from '../../core/channel';
import { Observable } from 'rxjs/Observable';
import { ActivatedRoute } from '@angular/router';
import 'rxjs/add/observable/of';

describe('ChannelHomeComponent', () => {
  let component: ChannelHomeComponent;
  let fixture: ComponentFixture<ChannelHomeComponent>;
  let channel: Channel;

  beforeEach(async(() => {

    channel = new Channel();
    channel.id = 123;
    channel.name = 'channel name';
    channel.description = 'channel description';
    channel.lastUpdatedOn = 1523198394721;

    TestBed.configureTestingModule({
      declarations: [
        ChannelHomeComponent
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            parent: {
              data: Observable.of(
                {
                  channel: channel
                }
              )
            }
          }
        }
      ],
      imports: [
        MatCardModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChannelHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
