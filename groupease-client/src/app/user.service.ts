import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {User} from './user';

@Injectable()
export class UserService {

  private userUrl = 'api/users';

  private currentUser: Observable<User>;

  constructor(
    private http: HttpClient
  ) { }

  /**
   * Returns the options for HTTP calls, including authentication headers.
   * Not using AuthService to avoid circular dependency; AuthService uses UserService.
   *
   * @returns {{headers: HttpHeaders}}
   */
  private getHttpOptions(): {headers: HttpHeaders} {
    return {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('access_token')}`)
    };
  }

  /**
   * Fetches a list of all users from the server.
   *
   * @returns {Observable<User[]>}
   */
  listAll(): Observable<User[]> {
    return this.http.get<User[]>(
      this.userUrl,
      this.getHttpOptions()
    );
  }

  /**
   * Fetches a user from the server by its ID.
   *
   * @param {number} id
   * @returns {Observable<User>}
   */
  getUser(id: number): Observable<User> {
    return this.http.get<User>(
      `${this.userUrl}/${id}`,
      this.getHttpOptions()
    );
  }

  /**
   * Refreshes the user data on the server, caching it, and returning it.
   *
   * @returns {Observable<User>} the current user.
   */
  saveCurrentUser(): Observable<User> {
    this.currentUser = this.http.put<User>(
      `${this.userUrl}/current`,
      null,
      this.getHttpOptions()
    );

    return this.currentUser;
  }

  /**
   * Returns the cached current user, fetching it if necessary.
   *
   * @returns {Observable<User>} the current user.
   */
  getCurrentUser(): Observable<User> {
    if (!this.currentUser) {
      this.saveCurrentUser();
    }

    return this.currentUser;
  }

  /**
   * Clears the cached currentUser.
   */
  clearCachedCurrentUser(): void {
    this.currentUser = null;
  }

}
