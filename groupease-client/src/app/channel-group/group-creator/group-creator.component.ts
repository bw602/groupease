import { Component, OnDestroy, OnInit } from '@angular/core';
import { ValidationErrors } from '@angular/forms';
import { Group } from '../../core/group';
import { GroupService } from '../../core/group.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material';
import { Channel } from '../../core/channel';
import { Subscription } from 'rxjs/Subscription';

@Component({
  selector: 'app-group-creator',
  templateUrl: './group-creator.component.html',
  styleUrls: ['./group-creator.component.scss']
})
export class GroupCreatorComponent implements OnInit, OnDestroy {

  public group: Group;

  private subscription: Subscription;

  constructor(
    private groupService: GroupService,
    private route: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.group = new Group();

    this.subscription = this.route.parent.parent.data.subscribe(
      (data: { channel: Channel }) => {
        this.group.channelId = data.channel.id;
      }
    );
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  onSubmit(): void {
    const groupSubscription = this.groupService.createGroup(this.group)
      .subscribe(
        (newGroup: Group) => {

          const snackBarRef = this.snackBar.open(
            'Group created',
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

          /* Navigate to group directory. */
          this.router.navigate(
            [
              '../directory'
            ],
            {
              relativeTo: this.route
            }
          );

        },
        () => {
          const snackBarRef = this.snackBar.open(
            'Group creation failed',
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
    this.subscription.add(groupSubscription);
  }

  getGroupNameErrorMessage(
    errors: ValidationErrors
  ): string {
    let errorMessage: string;

    if (errors.required) {
      errorMessage = 'Name is required';
    } else {
      errorMessage = 'Name is invalid';
    }

    return errorMessage;
  }

}
