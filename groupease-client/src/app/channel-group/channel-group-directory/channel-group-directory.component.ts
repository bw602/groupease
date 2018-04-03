import { Component, HostBinding, OnInit } from '@angular/core';

@Component({
  selector: 'app-channel-group-directory',
  templateUrl: './channel-group-directory.component.html',
  styleUrls: ['./channel-group-directory.component.scss']
})
export class ChannelGroupDirectoryComponent implements OnInit {

  /* Apply groupease-view CSS class to the component element. */
  @HostBinding('class.groupease-view') true;

  constructor() { }

  ngOnInit() {
  }

}
