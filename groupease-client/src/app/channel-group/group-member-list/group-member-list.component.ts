import { Component, HostBinding, Input, OnInit } from '@angular/core';
import { Member } from '../../core/member';

@Component({
  selector: 'app-group-member-list',
  templateUrl: './group-member-list.component.html',
  styleUrls: ['./group-member-list.component.scss']
})
export class GroupMemberListComponent implements OnInit {

  /* Apply CSS class to the component element. */
  @HostBinding('class.groupease-root') true;

  @Input()
  public members: Member[];

  constructor() { }

  ngOnInit() {
  }

}
