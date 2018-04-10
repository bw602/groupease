import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import { ChannelService } from '../core/channel.service';
import { Channel } from '../core/channel';

@Injectable()
export class ChannelResolverService implements Resolve<Channel> {

  constructor(
    private channelService: ChannelService,
    private router: Router
  ) { }

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<Channel> | Promise<Channel> | Channel {
    const id = +route.paramMap.get('id');

    return this.channelService.getChannel(id)
      .take(1)
      .map(
        (channel) => {
          if (channel) {
            return channel;
          } else {
            this.router.navigate(
              [
                '/'
              ]
            );
            return null;
          }
        }
      );
  }

}
