import { Component, HostBinding, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Group } from '../../core/group';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/switchMap';
import 'rxjs/add/observable/of';

@Component({
  selector: 'app-group-details',
  templateUrl: './group-details.component.html',
  styleUrls: ['./group-details.component.scss']
})
export class GroupDetailsComponent implements OnInit {

  /* Apply groupease-view CSS class to the component element. */
  @HostBinding('class.groupease-view') true;

  public groupObservable: Observable<Group>;

  constructor(
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.groupObservable = this.route.data.switchMap(
      (data: { group: Group }) => {
        return Observable.of(data.group);
      }
    );
  }

}
