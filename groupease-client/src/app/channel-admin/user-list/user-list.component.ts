import { Component, OnInit } from '@angular/core';
import { UserService } from '../../core/user.service';
import { Observable } from 'rxjs/Observable';
import { User } from '../../core/user';
import { Channel } from '../../core/channel';
import { ActivatedRoute } from '@angular/router';
import { ChannelInvitationService } from '../../core/channel-invitation.service';
import { ChannelInvitation } from '../../core/channel-invitation';
import { MatExpansionPanel, MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {

  userListObservable: Observable<User[]>;

  private channel: Channel;

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private channelInvitationService: ChannelInvitationService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.userListObservable = this.userService.listAll();

    this.route.parent.parent.parent.data.subscribe(
      (data: { channel: Channel }) => {
        this.channel = data.channel;
      }
    );
  }

  invite(
    user: User,
    userPanel: MatExpansionPanel
  ): void {

    const channelInvitation: ChannelInvitation = new ChannelInvitation();

    channelInvitation.recipient = user;
    channelInvitation.channel = this.channel;

    this.channelInvitationService.createInvitation(channelInvitation)
      .subscribe(
        () => {

          const snackBarRef = this.snackBar.open(
            'Channel invitation sent',
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
          userPanel.close();
        },
        () => {
          const snackBarRef = this.snackBar.open(
            'Channel invitation failed',
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
