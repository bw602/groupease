import { Component, HostBinding, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Group } from '../../core/group';
import { GroupService } from '../../core/group.service';
import { Channel } from '../../core/channel';
import { ActivatedRoute } from '@angular/router';
import 'rxjs/add/operator/switchMap';

@Component({
  selector: 'app-channel-group-directory',
  templateUrl: './channel-group-directory.component.html',
  styleUrls: ['./channel-group-directory.component.scss']
})
export class ChannelGroupDirectoryComponent implements OnInit {

  /* Apply groupease-view CSS class to the component element. */
  @HostBinding('class.groupease-view') true;

  groupListObservable: Observable<Group[]>;

  constructor(
    private route: ActivatedRoute,
    private groupService: GroupService
  ) { }

  ngOnInit(): void {
    this.groupListObservable = this.route.parent.parent.data.switchMap(
      (data: { channel: Channel }) => {
        return this.groupService.listAllInChannel(data.channel.id);
      }
    );
  }

}
