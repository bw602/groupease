import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Channel } from './channel';
import { Observable } from 'rxjs/Observable';
import { AuthService } from '../auth/auth.service';
import { UserService } from './user.service';
import 'rxjs/add/operator/switchMap';
import { User } from './user';

@Injectable()
export class ChannelService {

  private channelUrl = 'api/channels';

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private userService: UserService
  ) { }

  /**
   * Returns the options for HTTP calls, including authentication headers.
   *
   * @returns {{headers: HttpHeaders}}
   */
  private getHttpOptions(): {headers: HttpHeaders, params?: HttpParams} {
    return {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${this.authService.getAccessToken()}`)
    };
  }

  /**
   * Fetches a list of all channels from the server.
   *
   * @returns {Observable<Channel[]>}
   */
  listAll(): Observable<Channel[]> {
    return this.http.get<Channel[]>(
      this.channelUrl,
      this.getHttpOptions()
    );
  }

  /**
   * Fetches a list of channels where the current user is a member from the server.
   *
   * @returns {Observable<Channel[]>}
   */
  listWhereMember(): Observable<Channel[]> {

    return this.userService.getCurrentUser().switchMap(
      (currentUser: User) => {

        const params: HttpParams = new HttpParams()
          .set('userId', String(currentUser.id));

        const httpOptions = this.getHttpOptions();
        httpOptions.params = params;

        console.log(httpOptions);

        return this.http.get<Channel[]>(
          this.channelUrl,
          httpOptions
        );
      }
    );
  }

  /**
   * Fetches a channel from the server by its ID.
   *
   * @param {number} id
   * @returns {Observable<Channel>}
   */
  getChannel(id: number): Observable<Channel> {
    return this.http.get<Channel>(
      `${this.channelUrl}/${id}`,
      this.getHttpOptions()
    );
  }

  /**
   * Saves changes to a channel to the server.
   *
   * @param {Channel} channel
   * @returns {Observable<Channel>}
   */
  updateChannel(channel: Channel): Observable<Channel> {
    return this.http.put<Channel>(
      `${this.channelUrl}/${channel.id}`,
      channel,
      this.getHttpOptions()
    );
  }

  /**
   * Creates a new channel on the server.
   *
   * @param {Channel} channel
   * @returns {Observable<Channel>}
   */
  createChannel(channel: Channel): Observable<Channel> {
    return this.http.post<Channel>(
      this.channelUrl,
      channel,
      this.getHttpOptions()
    );
  }

  /**
   * Deletes a channel from the server by providing either the channel or its ID.
   *
   * @param {Channel | number} toDelete
   * @returns {Observable<Channel>}
   */
  deleteChannel(toDelete: Channel | number): Observable<Channel> {
    let id;

    if (typeof toDelete === 'number') {
      id = toDelete;
    } else {
      id = toDelete.id;
    }

    return this.http.delete<Channel>(
      `${this.channelUrl}/${id}`,
      this.getHttpOptions()
    );
  }

}
