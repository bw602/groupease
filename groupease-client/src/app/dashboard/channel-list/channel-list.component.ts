import { Component, OnInit } from '@angular/core';
import { ChannelService } from '../../core/channel.service';
import { Channel } from '../../core/channel';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-channel-list',
  templateUrl: './channel-list.component.html',
  styleUrls: ['./channel-list.component.scss']
})
export class ChannelListComponent implements OnInit {

  channelListObservable: Observable<Channel[]>;

  constructor(
    private channelService: ChannelService
  ) { }

  ngOnInit(): void {
    this.channelListObservable = this.channelService.listWhereMember();
  }

}
