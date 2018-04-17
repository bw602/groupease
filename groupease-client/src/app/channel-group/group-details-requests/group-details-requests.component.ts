import { Component, HostBinding, OnInit } from '@angular/core';

@Component({
  selector: 'app-group-details-requests',
  templateUrl: './group-details-requests.component.html',
  styleUrls: ['./group-details-requests.component.scss']
})
export class GroupDetailsRequestsComponent implements OnInit {

  /* Apply CSS class to the component element. */
  @HostBinding('class.groupease-view') true;

  constructor() { }

  ngOnInit(): void {
  }

}
