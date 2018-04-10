import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChannelDirectoryComponent } from './channel-directory.component';
import { MatButtonModule, MatCardModule, MatIconModule, MatListModule, MatTooltipModule } from '@angular/material';
import { of } from 'rxjs/observable/of';
import { ChannelService } from '../../core/channel.service';

describe('ChannelDirectoryComponent', () => {
  let component: ChannelDirectoryComponent;
  let fixture: ComponentFixture<ChannelDirectoryComponent>;
  let channelService: jasmine.SpyObj<ChannelService>;

  beforeEach(async(() => {

    channelService = jasmine.createSpyObj(
      'ChannelService',
      [
        'listAll'
      ]
    );

    TestBed.configureTestingModule({
      declarations: [
        ChannelDirectoryComponent
      ],
      providers: [
        {
          provide: ChannelService,
          useValue: channelService
        }
      ],
      imports: [
        MatButtonModule,
        MatCardModule,
        MatIconModule,
        MatListModule,
        MatTooltipModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChannelDirectoryComponent);
    component = fixture.componentInstance;
    channelService.listAll.and.returnValue(of([]));
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
