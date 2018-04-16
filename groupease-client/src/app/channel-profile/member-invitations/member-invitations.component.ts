import { Component, HostBinding, OnInit } from '@angular/core';

@Component({
  selector: 'app-member-invitations',
  templateUrl: './member-invitations.component.html',
  styleUrls: ['./member-invitations.component.scss']
})
export class MemberInvitationsComponent implements OnInit {

  /* Apply CSS class to the component element. */
  @HostBinding('class.groupease-view') true;

  constructor() { }

  ngOnInit() {
  }

}
