import { Component, HostBinding, OnInit } from '@angular/core';
import { Channel } from '../../core/channel';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-channel',
  templateUrl: './channel.component.html',
  styleUrls: ['./channel.component.scss']
})
export class ChannelComponent implements OnInit {

  /* Apply groupease-root CSS class to the component element. */
  @HostBinding('class.groupease-root') true;

  channel: Channel;

  constructor(
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.data.subscribe(
      (data: { channel: Channel }) => {
        this.channel = data.channel;
      }
    );
  }

}
