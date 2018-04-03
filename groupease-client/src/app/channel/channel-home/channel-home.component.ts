import { Component, HostBinding, OnInit } from '@angular/core';
import { Channel } from '../../core/channel';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-channel-home',
  templateUrl: './channel-home.component.html',
  styleUrls: ['./channel-home.component.scss']
})
export class ChannelHomeComponent implements OnInit {

  /* Apply groupease-view CSS class to the component element. */
  @HostBinding('class.groupease-view') true;

  channel: Channel;

  constructor(
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.parent.data.subscribe(
      (data: { channel: Channel }) => {
        this.channel = data.channel;
      }
    );
  }

}
