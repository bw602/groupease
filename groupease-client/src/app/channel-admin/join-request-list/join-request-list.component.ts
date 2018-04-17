import { Component, OnInit } from '@angular/core';
import { ChannelJoinRequestService } from '../../core/channel-join-request.service';
import { ActivatedRoute } from '@angular/router';
import { Channel } from '../../core/channel';
import 'rxjs/add/operator/switchMap';
import { Observable } from 'rxjs/Observable';
import { ChannelJoinRequest } from '../../core/channel-join-request';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-join-request-list',
  templateUrl: './join-request-list.component.html',
  styleUrls: ['./join-request-list.component.scss']
})
export class JoinRequestListComponent implements OnInit {

  channelJoinRequestListObservable: Observable<ChannelJoinRequest[]>;

  constructor(
    private route: ActivatedRoute,
    private channelJoinRequestService: ChannelJoinRequestService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.channelJoinRequestListObservable = this.route.parent.parent.parent.data.switchMap(
      (data: { channel: Channel }) => {
        return this.channelJoinRequestService.listAllForChannel(data.channel.id);
      }
    );
  }

  acceptRequest(
    channelJoinRequest: ChannelJoinRequest
  ): void {
    this.channelJoinRequestService.acceptRequest(channelJoinRequest)
      .subscribe(
        () => {
          const snackBarRef = this.snackBar.open(
            'Request approved',
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

          /* Reload list observable. */
          this.ngOnInit();

        },
        () => {
          const snackBarRef = this.snackBar.open(
            'Request approval failed',
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

  rejectRequest(
    channelJoinRequest: ChannelJoinRequest
  ): void {
    this.channelJoinRequestService.rejectRequest(channelJoinRequest)
      .subscribe(
        () => {
          const snackBarRef = this.snackBar.open(
            'Request rejected',
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

          /* Reload list observable. */
          this.ngOnInit();

        },
        () => {
          const snackBarRef = this.snackBar.open(
            'Request rejection failed',
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
