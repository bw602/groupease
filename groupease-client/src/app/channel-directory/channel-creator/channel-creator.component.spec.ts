import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChannelCreatorComponent } from './channel-creator.component';
import { MatButtonModule, MatCardModule, MatFormFieldModule, MatInputModule, MatSnackBarModule } from '@angular/material';
import { FormsModule } from '@angular/forms';
import { ChannelService } from '../../core/channel.service';
import { RouterTestingModule } from '@angular/router/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('ChannelCreatorComponent', () => {
  let component: ChannelCreatorComponent;
  let fixture: ComponentFixture<ChannelCreatorComponent>;
  let channelService: jasmine.SpyObj<ChannelService>;

  beforeEach(async(() => {

    channelService = jasmine.createSpyObj(
      'ChannelService',
      [
        'createChannel'
      ]
    );

    TestBed.configureTestingModule({
      declarations: [
        ChannelCreatorComponent
      ],
      providers: [
        {
          provide: ChannelService,
          useValue: channelService
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
    fixture = TestBed.createComponent(ChannelCreatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
