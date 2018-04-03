import { inject, TestBed } from '@angular/core/testing';

import { ChannelResolverService } from './channel-resolver.service';
import { RouterTestingModule } from '@angular/router/testing';
import { ChannelService } from '../core/channel.service';

describe('ChannelResolverService', () => {

  let channelService: jasmine.SpyObj<ChannelService>;

  beforeEach(() => {

    channelService = jasmine.createSpyObj(
      'ChannelService',
      [
        'listAll'
      ]
    );

    TestBed.configureTestingModule({
      providers: [
        ChannelResolverService,
        {
          provide: ChannelService,
          useValue: channelService
        }
      ],
      imports: [
        RouterTestingModule
      ]
    });
  });

  it('should be created', inject([ChannelResolverService], (service: ChannelResolverService) => {
    expect(service).toBeTruthy();
  }));

});
