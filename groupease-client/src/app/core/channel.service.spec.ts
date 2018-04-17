import { inject, TestBed } from '@angular/core/testing';

import { ChannelService } from './channel.service';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { AuthService } from '../auth/auth.service';
import { UserService } from './user.service';

describe('ChannelService', () => {

  let authService: jasmine.SpyObj<AuthService>;
  let userService: jasmine.SpyObj<UserService>;

  beforeEach(() => {

    authService = jasmine.createSpyObj(
      'AuthService',
      [
        'isAuthenticated'
      ]
    );

    userService = jasmine.createSpyObj(
      'UserService',
      [
        'getCurrentUser'
      ]
    );

    TestBed.configureTestingModule({
      providers: [
        ChannelService,
        {
          provide: AuthService,
          useValue: authService
        },
        {
          provide: UserService,
          useValue: userService
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
