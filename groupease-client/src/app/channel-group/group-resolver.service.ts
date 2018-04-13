import { Injectable } from '@angular/core';
import { Group } from '../core/group';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { GroupService } from '../core/group.service';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

@Injectable()
export class GroupResolverService implements Resolve<Group> {

  constructor(
    private groupService: GroupService,
    private router: Router
  ) { }

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<Group> | Promise<Group> | Group {

    const groupId: number = +route.paramMap.get('groupId');
    const channelId: number = route.parent.parent.data.channel.id;

    return this.groupService.getGroup(channelId, groupId)
      .take(1)
      .map(
        (group: Group) => {
          if (group) {
            return group;
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
