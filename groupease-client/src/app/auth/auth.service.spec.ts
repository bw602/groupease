import { TestBed, inject } from '@angular/core/testing';

import { AuthService } from './auth.service';
import {RouterTestingModule} from '@angular/router/testing';
import {UserService} from '../user.service';

describe('AuthService', () => {
  beforeEach(() => {

    const userService = jasmine.createSpyObj(
      'UserService',
      [
        'clearCachedCurrentUser',
        'saveCurrentUser'
      ]
    );

    TestBed.configureTestingModule({
      providers: [
        AuthService,
        {
          provide: UserService,
          useValue: userService
        }
      ],
      imports: [
        RouterTestingModule
      ]
    });

  });

  it('should be created', inject([AuthService], (service: AuthService) => {
    expect(service).toBeTruthy();
  }));

});
