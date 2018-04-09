import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ChannelService } from '../../core/channel.service';
import { Channel } from '../../core/channel';

@Component({
  selector: 'app-channel-list',
  templateUrl: './channel-list.component.html',
  styleUrls: ['./channel-list.component.scss']
})
export class ChannelListComponent implements OnInit, OnDestroy {

  channels: Array<Channel>;
  channelSubscription: Subscription;

  constructor(
    private channelService: ChannelService
  ) { }

  ngOnInit(): void {
    this.channelSubscription = this.channelService.listAll()
      .subscribe(
        channels => this.channels = channels
      );
  }

  ngOnDestroy(): void {
    this.channelSubscription.unsubscribe();
  }

}
