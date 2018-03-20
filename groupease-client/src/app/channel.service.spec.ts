import { TestBed, inject } from '@angular/core/testing';

import { ChannelService } from './channel.service';
import {HttpClientModule} from '@angular/common/http';
import {AuthService} from './auth/auth.service';
import {RouterTestingModule} from '@angular/router/testing';

describe('ChannelService', () => {
  beforeEach(() => {

    const authService = jasmine.createSpyObj(
      'AuthService',
      [
        'isAuthenticated'
      ]
    );

    TestBed.configureTestingModule({
      providers: [
        ChannelService,
        {
          provide: AuthService,
          useValue: authService
        }
      ],
      imports: [
        HttpClientModule,
        RouterTestingModule
      ]
    });
  });

  it('should be created', inject([ChannelService], (service: ChannelService) => {
    expect(service).toBeTruthy();
  }));

});
