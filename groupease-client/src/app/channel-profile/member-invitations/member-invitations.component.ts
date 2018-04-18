import { Component, HostBinding, OnInit } from '@angular/core';
import { GroupInvitationService } from '../../core/group-invitation.service';
import { UserService } from '../../core/user.service';
import { User } from '../../core/user';
import { Observable } from 'rxjs/Observable';
import { ActivatedRoute } from '@angular/router';
import { MatSnackBar } from '@angular/material';
import { Channel } from '../../core/channel';
import { GroupInvitation } from '../../core/group-invitation';
import 'rxjs/add/operator/switchMap';
import 'rxjs/add/observable/of';

@Component({
  selector: 'app-member-invitations',
  templateUrl: './member-invitations.component.html',
  styleUrls: ['./member-invitations.component.scss']
})
export class MemberInvitationsComponent implements OnInit {

  /* Apply CSS class to the component element. */
  @HostBinding('class.groupease-view') true;

  public groupInvitationListObservable: Observable<GroupInvitation[]>;

  constructor(
    private route: ActivatedRoute,
    private groupInvitationService: GroupInvitationService,
    private userService: UserService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    /* Get the channel from route and the currentUser, and use to fetch group invitations. */
    this.groupInvitationListObservable = this.route.parent.parent.parent.data.switchMap(
      (data: { channel: Channel}) => {
        return Observable.of(data.channel);
      }
    ).switchMap(
      (channel: Channel) => {
        return this.userService.getCurrentUser().switchMap(
          (currentUser: User) => {
            return this.groupInvitationService.listAllForUserInChannel(
              currentUser.id,
              channel.id
            );
          }
        );
      }
    );
  }

  acceptInvitation(
    groupInvitation: GroupInvitation
  ): void {
    console.log(groupInvitation);
    this.groupInvitationService.acceptInvitation(groupInvitation)
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
    groupInvitation: GroupInvitation
  ): void {
    this.groupInvitationService.rejectInvitation(groupInvitation)
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
