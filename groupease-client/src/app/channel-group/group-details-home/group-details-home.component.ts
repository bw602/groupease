import { Component, HostBinding, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Group } from '../../core/group';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-group-details-home',
  templateUrl: './group-details-home.component.html',
  styleUrls: ['./group-details-home.component.scss']
})
export class GroupDetailsHomeComponent implements OnInit {

  /* Apply CSS class to the component element. */
  @HostBinding('class.groupease-view') true;

  public groupObservable: Observable<Group>;

  constructor(
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.groupObservable = this.route.parent.data.switchMap(
      (data: { group: Group }) => {
        return Observable.of(data.group);
      }
    );
  }

}
