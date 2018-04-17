import { TestBed, inject } from '@angular/core/testing';

import { ChannelJoinRequestService } from './channel-join-request.service';
import { AuthService } from '../auth/auth.service';
import { HttpClientModule } from '@angular/common/http';

describe('ChannelJoinRequestService', () => {

  let authService: jasmine.SpyObj<AuthService>;

  beforeEach(() => {

    authService = jasmine.createSpyObj(
      'AuthService',
      [
        'isAuthenticated'
      ]
    );

    TestBed.configureTestingModule({
      providers: [
        ChannelJoinRequestService,
        {
          provide: AuthService,
          useValue: authService
        }
      ],
      imports: [
        HttpClientModule
      ]
    });
  });

  it('should be created', inject([ChannelJoinRequestService], (service: ChannelJoinRequestService) => {
    expect(service).toBeTruthy();
  }));

});
