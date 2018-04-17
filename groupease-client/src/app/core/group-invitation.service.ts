import { Injectable } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { GroupInvitation } from './group-invitation';

@Injectable()
export class GroupInvitationService {

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
   * Fetches a list of all group invitations in the channel for the recipient user.
   *
   * @param {number} recipientUserId
   * @param {number} channelId
   * @returns {Observable<GroupInvitation[]>}
   */
  listAllForUserInChannel(
    recipientUserId: number,
    channelId: number
  ): Observable<GroupInvitation[]> {

    const url = `api/users/${recipientUserId}/channels/${channelId}/group-invitations`;

    return this.http.get<GroupInvitation[]>(
      url,
      this.getHttpOptions()
    );
  }

  /**
   * Fetches a single group invitation from the server from all its IDs.
   *
   * @param {number} recipientUserId
   * @param {number} channelId
   * @param {number} invitationId
   * @returns {Observable<GroupInvitation>}
   */
  getInvitation(
    recipientUserId: number,
    channelId: number,
    invitationId: number
  ): Observable<GroupInvitation> {

    const url = `api/users/${recipientUserId}/channels/${channelId}/group-invitations/${invitationId}`;

    return this.http.get<GroupInvitation>(
      url,
      this.getHttpOptions()
    );
  }

  /**
   * Creates a new group invitation on the server.
   *
   * @param {GroupInvitation} groupInvitation
   * @returns {Observable<GroupInvitation>}
   */
  createInvitation(
    groupInvitation: GroupInvitation
  ): Observable<GroupInvitation> {

    const recipientUserId = groupInvitation.recipient.groupeaseUser.id;
    const channelId = groupInvitation.group.channelId;
    const url = `api/users/${recipientUserId}/channels/${channelId}/group-invitations`;

    return this.http.post<GroupInvitation>(
      url,
      groupInvitation,
      this.getHttpOptions()
    );
  }

  /**
   * Accepts a group invitation, adding the user as a group member.
   *
   * @param {GroupInvitation} groupInvitation
   * @returns {Observable<GroupInvitation>}
   */
  acceptInvitation(
    groupInvitation: GroupInvitation
  ): Observable<GroupInvitation> {

    const recipientUserId = groupInvitation.recipient.groupeaseUser.id;
    const channelId = groupInvitation.group.channelId;
    const invitationId = groupInvitation.id;
    const url = `api/users/${recipientUserId}/channels/${channelId}/group-invitations/${invitationId}/acceptance`;

    return this.http.post<GroupInvitation>(
      url,
      groupInvitation,
      this.getHttpOptions()
    );
  }

  /**
   * Rejects a group invitation, deleting it.
   *
   * @param {GroupInvitation} groupInvitation
   * @returns {Observable<GroupInvitation>}
   */
  rejectInvitation(
    groupInvitation: GroupInvitation
  ): Observable<GroupInvitation> {

    const recipientUserId = groupInvitation.recipient.groupeaseUser.id;
    const channelId = groupInvitation.group.channelId;
    const invitationId = groupInvitation.id;
    const url = `api/users/${recipientUserId}/channels/${channelId}/group-invitations/${invitationId}/rejection`;

    return this.http.post<GroupInvitation>(
      url,
      groupInvitation,
      this.getHttpOptions()
    );
  }

  /**
   * Deletes a group invitation from the server.
   *
   * @param {GroupInvitation} groupInvitation
   * @returns {Observable<GroupInvitation>}
   */
  deleteInvitation(
    groupInvitation: GroupInvitation
  ): Observable<GroupInvitation> {

    const recipientUserId = groupInvitation.recipient.groupeaseUser.id;
    const channelId = groupInvitation.group.channelId;
    const invitationId = groupInvitation.id;
    const url = `api/users/${recipientUserId}/channels/${channelId}/group-invitations/${invitationId}`;

    return this.http.delete<GroupInvitation>(
      url,
      this.getHttpOptions()
    );
  }

}
