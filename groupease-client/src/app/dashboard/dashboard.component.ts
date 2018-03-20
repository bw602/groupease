import {Component, HostBinding, OnDestroy, OnInit} from '@angular/core';
import {ChannelService} from '../channel.service';
import {Channel} from '../channel';
import {Subscription} from 'rxjs/Subscription';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit, OnDestroy {

  channels: Array<Channel>;
  channelSubscription: Subscription;

  /* Apply groupease-view CSS class to the component element. */
  @HostBinding('class.groupease-view') true;

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
