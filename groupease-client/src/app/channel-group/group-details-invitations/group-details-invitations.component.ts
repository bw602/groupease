import { Component, HostBinding, OnInit } from '@angular/core';

@Component({
  selector: 'app-group-details-invitations',
  templateUrl: './group-details-invitations.component.html',
  styleUrls: ['./group-details-invitations.component.scss']
})
export class GroupDetailsInvitationsComponent implements OnInit {

  /* Apply CSS class to the component element. */
  @HostBinding('class.groupease-view') true;

  constructor() { }

  ngOnInit(): void {
  }

}
