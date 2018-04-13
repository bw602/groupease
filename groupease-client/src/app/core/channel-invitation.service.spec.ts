import { TestBed, inject } from '@angular/core/testing';

import { ChannelInvitationService } from './channel-invitation.service';
import { AuthService } from '../auth/auth.service';
import { HttpClientModule } from '@angular/common/http';

describe('ChannelInvitationService', () => {

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
        ChannelInvitationService,
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

  it('should be created', inject([ChannelInvitationService], (service: ChannelInvitationService) => {
    expect(service).toBeTruthy();
  }));

});
