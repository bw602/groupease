import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../auth/auth.service';
import { Observable } from 'rxjs/Observable';
import { Group } from './group';

@Injectable()
export class GroupService {

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }

  /**
   * Returns the options for HTTP calls, including authentication headers.
   *
   * @returns {{headers: HttpHeaders}}
   */
  private getHttpOptions(): {headers: HttpHeaders} {
    return {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${this.authService.getAccessToken()}`)
    };
  }

  /**
   * Fetches from the server a list of all groups in the channel.
   *
   * @param {number} channelId
   * @returns {Observable<Group[]>}
   */
  listAllInChannel(
    channelId: number
  ): Observable<Group[]> {

    const url = `api/channels/${channelId}/groups`;

    return this.http.get<Group[]>(
      url,
      this.getHttpOptions()
    );
  }

  /**
   * Fetches from the server the group that matches the IDs provided.
   *
   * @param {number} channelId
   * @param {number} groupId
   * @returns {Observable<Group>}
   */
  getGroup(
    channelId: number,
    groupId: number
  ): Observable<Group> {

    const url = `api/channels/${channelId}/groups/${groupId}`;

    return this.http.get<Group>(
      url,
      this.getHttpOptions()
    );
  }

  /**
   * Creates a new group on the server.
   *
   * @param {Group} group
   * @returns {Observable<Group>}
   */
  createGroup(
    group: Group
  ): Observable<Group> {

    const url = `api/channels/${group.channelId}/groups`;

    return this.http.post<Group>(
      url,
      group,
      this.getHttpOptions()
    );
  }

  /**
   * Updates an existing group on the server.
   *
   * @param {Group} group
   * @returns {Observable<Group>}
   */
  updateGroup(
    group: Group
  ): Observable<Group> {

    const url = `api/channels/${group.channelId}/groups/${group.id}`;

    return this.http.put<Group>(
      url,
      group,
      this.getHttpOptions()
    );
  }

}
