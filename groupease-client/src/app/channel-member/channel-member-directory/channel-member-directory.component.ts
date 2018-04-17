import { Component, HostBinding, OnInit } from '@angular/core';
import { Member } from '../../core/member';
import { Observable } from 'rxjs/Observable';
import { MemberService } from '../../core/member.service';
import { ActivatedRoute } from '@angular/router';
import 'rxjs/add/operator/switchMap';
import { Channel } from '../../core/channel';

@Component({
  selector: 'app-channel-member-directory',
  templateUrl: './channel-member-directory.component.html',
  styleUrls: ['./channel-member-directory.component.scss']
})
export class ChannelMemberDirectoryComponent implements OnInit {

  /* Apply groupease-view CSS class to the component element. */
  @HostBinding('class.groupease-view') true;

  public memberListObservable: Observable<Member[]>;

  constructor(
    private route: ActivatedRoute,
    private memberService: MemberService
  ) { }

  ngOnInit(): void {
    this.memberListObservable = this.route.parent.parent.data.switchMap(
      (data: { channel: Channel}) => {
        const channelId: number = data.channel.id;
        return this.memberService.listAllInChannel(channelId);
      }
    );
  }

}
