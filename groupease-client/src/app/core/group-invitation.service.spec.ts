import { TestBed, inject } from '@angular/core/testing';

import { GroupInvitationService } from './group-invitation.service';
import { AuthService } from '../auth/auth.service';
import { HttpClientModule } from '@angular/common/http';

describe('GroupInvitationService', () => {

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
        GroupInvitationService,
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

  it('should be created', inject([GroupInvitationService], (service: GroupInvitationService) => {
    expect(service).toBeTruthy();
  }));

});
