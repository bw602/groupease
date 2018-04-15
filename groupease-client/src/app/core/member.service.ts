import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../auth/auth.service';
import { Observable } from 'rxjs/Observable';
import { Member } from './member';

@Injectable()
export class MemberService {

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
   * Fetches from the server a list of all members in the channel.
   *
   * @param {number} channelId
   * @returns {Observable<Member[]>}
   */
  listAllInChannel(
    channelId: number
  ): Observable<Member[]> {

    const url = `api/channels/${channelId}/members`;

    return this.http.get<Member[]>(
      url,
      this.getHttpOptions()
    );
  }

  /**
   * Fetches from the server the member that matches the IDs provided.
   *
   * @param {number} channelId
   * @param {number} memberId
   * @returns {Observable<Member>}
   */
  getMember(
    channelId: number,
    memberId: number
  ): Observable<Member> {

    const url = `api/channels/${channelId}/members/${memberId}`;

    return this.http.get<Member>(
      url,
      this.getHttpOptions()
    );
  }

  /**
   * Fetches from the server the channel member for the current user.
   *
   * @param {number} channelId
   * @returns {Observable<Member>}
   */
  getMemberForCurrentUser(
    channelId: number
  ): Observable<Member> {

    const url = `api/channels/${channelId}/members/current`;

    return this.http.get<Member>(
      url,
      this.getHttpOptions()
    );
  }

  /**
   * Updates an existing channel member on the server.
   *
   * @param {Member} member
   * @returns {Observable<Member>}
   */
  updateMember(
    member: Member
  ): Observable<Member> {

    const url = `api/channels/${member.channel.id}/members/${member.id}`;

    return this.http.put<Member>(
      url,
      member,
      this.getHttpOptions()
    );
  }

  /**
   * Deletes a channel member from the server.
   *
   * @param {Member} toDelete
   * @returns {Observable<Member>}
   */
  deleteMember(
    toDelete: Member
  ): Observable<Member> {

    const url = `api/channels/${toDelete.channel.id}/members/${toDelete.id}`;

    return this.http.delete<Member>(
      url,
      this.getHttpOptions()
    );
  }

}
