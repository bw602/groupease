import {inject, TestBed} from '@angular/core/testing';

import {AuthGuard} from './auth.guard';
import {RouterTestingModule} from '@angular/router/testing';
import {AuthService} from './auth.service';

describe('AuthGuard', () => {
  beforeEach(() => {

    const authService = jasmine.createSpyObj(
      'AuthService',
      [
        'isAuthenticated'
      ]
    );

    TestBed.configureTestingModule({
      providers: [
        AuthGuard,
        {
          provide: AuthService,
          useValue: authService
        }
      ],
      imports: [
        RouterTestingModule
      ]
    });
  });

  it(
    'should return true when user is authenticated',
    inject(
      [AuthGuard, AuthService],
      (guard: AuthGuard, authService: jasmine.SpyObj<AuthService>) => {

        authService.isAuthenticated.and.returnValue(true);

        expect(guard.canActivate(null, null)).toBe(true);
      }
    )
  );

  it(
    'should return false and route to root when not authenticated',
    inject(
      [AuthGuard, AuthService],
      (guard: AuthGuard, authService: jasmine.SpyObj<AuthService>) => {

        authService.isAuthenticated.and.returnValue(false);

        expect(guard.canActivate(null, null)).toBe(false);
      }
    )
  );

});
