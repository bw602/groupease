import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChannelDirectoryComponent } from './channel-directory.component';
import { MatCardModule, MatIconModule, MatListModule } from '@angular/material';
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
        MatCardModule,
        MatIconModule,
        MatListModule
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
