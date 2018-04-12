import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../auth/auth.service';
import { Observable } from 'rxjs/Observable';
import { ChannelJoinRequest } from './channel-join-request';

@Injectable()
export class ChannelJoinRequestService {

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
   * Fetches a list of all channel join requests for the provided channel ID from the server.
   *
   * @param {number} channelId
   * @returns {Observable<ChannelJoinRequest[]>}
   */
  listAllForChannel(
    channelId: number
  ): Observable<ChannelJoinRequest[]> {

    const url = `api/channels/${channelId}/join-requests`;

    return this.http.get<ChannelJoinRequest[]>(
      url,
      this.getHttpOptions()
    );
  }

  /**
   * Fetches a channel join request from the server by its channel and request IDs.
   *
   * @param {number} channelId
   * @param {number} channelJoinRequestId
   * @returns {Observable<ChannelJoinRequest>}
   */
  getRequest(
    channelId: number,
    channelJoinRequestId: number
  ): Observable<ChannelJoinRequest> {

    const url = `api/channels/${channelId}/join-requests/${channelJoinRequestId}`;

    return this.http.get<ChannelJoinRequest>(
      url,
      this.getHttpOptions()
    );
  }

  /**
   * Creates a new channel join request on the server.
   *
   * @param {ChannelJoinRequest} channelJoinRequest
   * @returns {Observable<ChannelJoinRequest>}
   */
  createRequest(
    channelJoinRequest: ChannelJoinRequest
  ): Observable<ChannelJoinRequest> {

    const channelId = channelJoinRequest.channel.id,
      url = `api/channels/${channelId}/join-requests`;

    return this.http.post<ChannelJoinRequest>(
      url,
      channelJoinRequest,
      this.getHttpOptions()
    );
  }

  /**
   * Accepts a channel join request, adding the requestor as a member.
   *
   * @param {ChannelJoinRequest} channelJoinRequest
   * @returns {Observable<ChannelJoinRequest>}
   */
  acceptRequest(
    channelJoinRequest: ChannelJoinRequest
  ): Observable<ChannelJoinRequest> {

    const channelId = channelJoinRequest.channel.id,
      channelJoinRequestId = channelJoinRequest.id,
      url = `api/channels/${channelId}/join-requests/${channelJoinRequestId}/acceptance`;

    return this.http.post<ChannelJoinRequest>(
      url,
      channelJoinRequest,
      this.getHttpOptions()
    );
  }

  /**
   * Rejects a channel join request, deleting it.
   *
   * @param {ChannelJoinRequest} channelJoinRequest
   * @returns {Observable<ChannelJoinRequest>}
   */
  rejectRequest(
    channelJoinRequest: ChannelJoinRequest
  ): Observable<ChannelJoinRequest> {
    const channelId = channelJoinRequest.channel.id,
      channelJoinRequestId = channelJoinRequest.id,
      url = `api/channels/${channelId}/join-requests/${channelJoinRequestId}/rejection`;

    return this.http.post<ChannelJoinRequest>(
      url,
      channelJoinRequest,
      this.getHttpOptions()
    );
  }

  /**
   * Deletes a channel join request from the server.
   *
   * @param {ChannelJoinRequest} channelJoinRequest
   * @returns {Observable<ChannelJoinRequest>}
   */
  deleteRequest(
    channelJoinRequest: ChannelJoinRequest
  ): Observable<ChannelJoinRequest> {
    const channelId = channelJoinRequest.channel.id,
      channelJoinRequestId = channelJoinRequest.id,
      url = `api/channels/${channelId}/join-requests/${channelJoinRequestId}`;

    return this.http.delete<ChannelJoinRequest>(
      url,
      this.getHttpOptions()
    );
  }

}
