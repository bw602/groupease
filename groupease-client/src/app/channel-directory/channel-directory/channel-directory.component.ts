import { Component, HostBinding, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ChannelService } from '../../core/channel.service';
import { Channel } from '../../core/channel';
import { ChannelJoinRequest } from '../../core/channel-join-request';
import { ChannelJoinRequestService } from '../../core/channel-join-request.service';
import { MatExpansionPanel, MatSnackBar } from '@angular/material';

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
    private channelService: ChannelService,
    private channelJoinRequestService: ChannelJoinRequestService,
    private snackBar: MatSnackBar
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

  sendJoinRequest(
    channel: Channel,
    channelPanel: MatExpansionPanel,
    comments?: string
  ) {
    const channelJoinRequest = new ChannelJoinRequest();
    channelJoinRequest.channel = channel;

    if (comments) {
      channelJoinRequest.comments = comments;
    }

    this.channelJoinRequestService.createRequest(channelJoinRequest)
      .subscribe(
        () => {

          const snackBarRef = this.snackBar.open(
            'Channel join request sent',
            'DISMISS',
            {
              duration: 2000
            }
          );

          /* Dismiss notification if dismiss clicked. */
          snackBarRef.onAction().subscribe(
            () => {
              snackBarRef.dismiss();
            }
          );

          /* Collapse the panel. */
          channelPanel.close();
        },
        () => {
          const snackBarRef = this.snackBar.open(
            'Channel join request failed',
            'DISMISS',
            {
              duration: 2000
            }
          );

          /* Dismiss notification if dismiss clicked. */
          snackBarRef.onAction().subscribe(
            () => {
              snackBarRef.dismiss();
            }
          );
        }
      );
  }

}
