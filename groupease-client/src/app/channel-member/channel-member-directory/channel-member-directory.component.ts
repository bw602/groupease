import { Component, HostBinding, OnInit } from '@angular/core';

@Component({
  selector: 'app-channel-member-directory',
  templateUrl: './channel-member-directory.component.html',
  styleUrls: ['./channel-member-directory.component.scss']
})
export class ChannelMemberDirectoryComponent implements OnInit {

  /* Apply groupease-view CSS class to the component element. */
  @HostBinding('class.groupease-view') true;

  constructor() { }

  ngOnInit() {
  }

}
