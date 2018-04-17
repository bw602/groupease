import { Component, HostBinding, Input, OnInit } from '@angular/core';
import { Group } from '../../core/group';

@Component({
  selector: 'app-group-summary',
  templateUrl: './group-summary.component.html',
  styleUrls: ['./group-summary.component.scss']
})
export class GroupSummaryComponent implements OnInit {

  /* Apply CSS class to the component element. */
  @HostBinding('class.groupease-root') true;

  @Input()
  public group: Group;

  constructor() { }

  ngOnInit() {
  }

}
