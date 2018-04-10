import { Component, HostBinding, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ChannelService } from '../../core/channel.service';
import { Channel } from '../../core/channel';

@Component({
  selector: 'app-channel-directory',
  templateUrl: './channel-directory.component.html',
  styleUrls: ['./channel-directory.component.scss']
})
export class ChannelDirectoryComponent implements OnInit, OnDestroy {

  /* Apply groupease-view CSS class to the component element. */
  @HostBinding('class.groupease-view') true;

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
