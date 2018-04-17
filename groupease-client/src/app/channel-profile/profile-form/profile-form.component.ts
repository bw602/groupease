import { Component, HostBinding, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Channel } from '../../core/channel';
import { MemberService } from '../../core/member.service';
import { Subscription } from 'rxjs/Subscription';
import { Member } from '../../core/member';
import { MatSnackBar } from '@angular/material';
import 'rxjs/add/operator/switchMap';

@Component({
  selector: 'app-profile-form',
  templateUrl: './profile-form.component.html',
  styleUrls: ['./profile-form.component.scss']
})
export class ProfileFormComponent implements OnInit, OnDestroy {

  /* Apply CSS class to the component element. */
  @HostBinding('class.groupease-view') true;

  public member: Member;

  private subscription: Subscription;

  constructor(
    private route: ActivatedRoute,
    private memberService: MemberService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.subscription = this.route.parent.parent.parent.data.switchMap(
      (data: { channel: Channel}) => {
        const channelId: number = data.channel.id;
        return this.memberService.getMemberForCurrentUser(channelId);
      }
    ).subscribe(
      (member: Member) => {
        this.member = member;
      }
    );
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  onSubmit(): void {
    const memberSubscription = this.memberService.updateMember(this.member)
      .subscribe(
        (updatedMember: Member) => {
          this.member = updatedMember;

          const snackBarRef = this.snackBar.open(
            'Member profile saved',
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
        },
        () => {
          const snackBarRef = this.snackBar.open(
            'Member profile save failed',
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

    /* Add subscription to component subscription to be cleaned up. */
    this.subscription.add(memberSubscription);
  }

}
