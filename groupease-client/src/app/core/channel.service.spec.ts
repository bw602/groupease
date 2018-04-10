import { inject, TestBed } from '@angular/core/testing';

import { ChannelService } from './channel.service';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { AuthService } from '../auth/auth.service';

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
