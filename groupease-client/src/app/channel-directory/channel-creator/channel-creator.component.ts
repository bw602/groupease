import { Component, HostBinding, OnInit } from '@angular/core';
import { Channel } from '../../core/channel';
import { ValidationErrors } from '@angular/forms';
import { ChannelService } from '../../core/channel.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-channel-creator',
  templateUrl: './channel-creator.component.html',
  styleUrls: ['./channel-creator.component.scss']
})
export class ChannelCreatorComponent implements OnInit {

  /* Apply groupease-view CSS class to the component element. */
  @HostBinding('class.groupease-view') true;

  public channel: Channel;

  constructor(
    private channelService: ChannelService,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    this.channel = new Channel();
  }

  public onSubmit() {
    this.channelService.createChannel(this.channel)
      .subscribe(
        (newChannel: Channel) => {

          const snackBarRef = this.snackBar.open(
            'Channel created',
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

          /* Navigate to new channel home. */
          this.router.navigate(
            [
              '/views/channels',
              newChannel.id
            ]
          );
        },
        () => {
          const snackBarRef = this.snackBar.open(
            'Channel creation failed',
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

  public getChannelNameErrorMessage(
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
