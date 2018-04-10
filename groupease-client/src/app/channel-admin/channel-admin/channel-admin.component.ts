import { Component, HostBinding, OnInit } from '@angular/core';

@Component({
  selector: 'app-channel-admin',
  templateUrl: './channel-admin.component.html',
  styleUrls: ['./channel-admin.component.scss']
})
export class ChannelAdminComponent implements OnInit {

  /* Apply groupease-view CSS class to the component element. */
  @HostBinding('class.groupease-view') true;

  constructor() { }

  ngOnInit() {
  }

}
