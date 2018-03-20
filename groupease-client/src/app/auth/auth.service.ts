import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import * as auth0 from 'auth0-js';

@Injectable()
export class AuthService {

  private homeRoute = 'views/home';

  auth0 = new auth0.WebAuth(
    {
      clientID: 'ieeYJTPZfw1jZTvwHjV4H9C4EcMrT1Cx',
      domain: 'mckoon.auth0.com',
      responseType: 'token id_token',
      audience: 'https://groupease.herokuapp.com',
      redirectUri: `${window.location.origin}/views/callback`,
      scope: 'openid profile email'
    }
  );

  constructor(
    public router: Router
  ) {}

  public login(): void {
    this.auth0.authorize();
  }

  public handleAuthentication(): void {
    this.auth0.parseHash((err, authResult) => {
      if (authResult && authResult.accessToken && authResult.idToken) {
        this.setSession(authResult);
        this.router.navigate([this.homeRoute]);
      } else if (err) {
        this.router.navigate([this.homeRoute]);
        console.log(err);
        alert(`Error: ${err.error}. Check the console for further details.`);
      }
    });
  }

  private setSession(authResult): void {
    // Set the time that the access token will expire at
    const expiresAt = JSON.stringify((authResult.expiresIn * 1000) + new Date().getTime());
    localStorage.setItem('access_token', authResult.accessToken);
    localStorage.setItem('id_token', authResult.idToken);
    localStorage.setItem('expires_at', expiresAt);
  }

  public logout(): void {
    // Remove tokens and expiry time from localStorage
    localStorage.removeItem('access_token');
    localStorage.removeItem('id_token');
    localStorage.removeItem('expires_at');
    // Go back to the home route
    this.router.navigate([this.homeRoute]);
  }

  public isAuthenticated(): boolean {
    // Check whether the current time is past the
    // access token's expiry time
    const expiresAt = JSON.parse(localStorage.getItem('expires_at'));
    return new Date().getTime() < expiresAt;
  }

  /**
   * Returns the access token for the currently authenticated user, or null if not logged in.
   *
   * @returns {string} access token.
   */
  public getAccessToken(): string {
    if (this.isAuthenticated()) {
      return localStorage.getItem('access_token');
    }

    return null;
  }

}
