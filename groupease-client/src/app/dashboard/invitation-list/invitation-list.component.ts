import { Component, OnInit } from '@angular/core';
import { UserService } from '../../core/user.service';
import { User } from '../../core/user';
import { ChannelInvitationService } from '../../core/channel-invitation.service';
import { ChannelInvitation } from '../../core/channel-invitation';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/switchMap';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-invitation-list',
  templateUrl: './invitation-list.component.html',
  styleUrls: ['./invitation-list.component.scss']
})
export class InvitationListComponent implements OnInit {

  private currentUser: User;

  channelInvitationListObservable: Observable<ChannelInvitation[]>;

  constructor(
    private userService: UserService,
    private channelInvitationService: ChannelInvitationService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.channelInvitationListObservable = this.userService.getCurrentUser()
      .switchMap(
        (user) => {
          this.currentUser = user;
          return this.channelInvitationService.listAllForUser(user.id);
        }
      );
  }

  acceptInvitation(
    channelInvitation: ChannelInvitation
  ): void {
    this.channelInvitationService.acceptInvitation(channelInvitation)
      .subscribe(
        () => {
          const snackBarRef = this.snackBar.open(
            'Invitation accepted',
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
            'Invitation acceptance failed',
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

  rejectInvitation(
    channelInvitation: ChannelInvitation
  ): void {
    this.channelInvitationService.rejectInvitation(channelInvitation)
      .subscribe(
        () => {
          const snackBarRef = this.snackBar.open(
            'Invitation rejected',
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
            'Invitation rejection failed',
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
