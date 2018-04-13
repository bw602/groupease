import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../auth/auth.service';
import { ChannelInvitation } from './channel-invitation';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class ChannelInvitationService {

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
   * Fetches a list of all channel invitations from the server for the provided recipient.
   *
   * @param {number} userId
   * @returns {Observable<ChannelInvitation[]>}
   */
  listAllForUser(
    userId: number
  ): Observable<ChannelInvitation[]> {

    const url = `api/users/${userId}/channel-invitations`;

    return this.http.get<ChannelInvitation[]>(
      url,
      this.getHttpOptions()
    );
  }

  /**
   * Fetches a channel invitation from the server by its user and invitation IDs.
   *
   * @param {number} userId
   * @param {number} channelInvitationId
   * @returns {Observable<ChannelInvitation>}
   */
  getInvitation(
    userId: number,
    channelInvitationId: number
  ): Observable<ChannelInvitation> {

    const url = `api/users/${userId}/channel-invitations/${channelInvitationId}`;

    return this.http.get<ChannelInvitation>(
      url,
      this.getHttpOptions()
    );
  }

  /**
   * Creates a channel invitation on the server.
   *
   * @param {ChannelInvitation} channelInvitation
   * @returns {Observable<ChannelInvitation>}
   */
  createInvitation(
    channelInvitation: ChannelInvitation
  ): Observable<ChannelInvitation> {

    const userId = channelInvitation.recipient.id;
    const url = `api/users/${userId}/channel-invitations`;

    return this.http.post<ChannelInvitation>(
      url,
      channelInvitation,
      this.getHttpOptions()
    );
  }

  /**
   * Accepts a channel invitation, adding the user as a channel member.
   *
   * @param {ChannelInvitation} channelInvitation
   * @returns {Observable<ChannelInvitation>}
   */
  acceptInvitation(
    channelInvitation: ChannelInvitation
  ):  Observable<ChannelInvitation> {

    const userId = channelInvitation.recipient.id;
    const invitationId = channelInvitation.id;
    const url = `api/users/${userId}/channel-invitations/${invitationId}/acceptance`;

    return this.http.post<ChannelInvitation>(
      url,
      channelInvitation,
      this.getHttpOptions()
    );
  }

  /**
   * Rejects a channel invitation, deleting it.
   *
   * @param {ChannelInvitation} channelInvitation
   * @returns {Observable<ChannelInvitation>}
   */
  rejectInvitation(
    channelInvitation: ChannelInvitation
  ):  Observable<ChannelInvitation> {

    const userId = channelInvitation.recipient.id;
    const invitationId = channelInvitation.id;
    const url = `api/users/${userId}/channel-invitations/${invitationId}/rejection`;

    return this.http.post<ChannelInvitation>(
      url,
      channelInvitation,
      this.getHttpOptions()
    );
  }

  /**
   * Deletes a channel invitation from the server.
   *
   * @param {ChannelInvitation} channelInvitation
   * @returns {Observable<ChannelInvitation>}
   */
  deleteInvitation(
    channelInvitation: ChannelInvitation
  ):  Observable<ChannelInvitation> {

    const userId = channelInvitation.recipient.id;
    const invitationId = channelInvitation.id;
    const url = `api/users/${userId}/channel-invitations/${invitationId}`;

    return this.http.delete<ChannelInvitation>(
      url,
      this.getHttpOptions()
    );
  }

}
