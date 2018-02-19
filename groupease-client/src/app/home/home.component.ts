import {Component, HostBinding, OnInit} from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  /* Apply groupease-view CSS class to the component element. */
  @HostBinding('class.groupease-view') true;

  constructor() { }

  ngOnInit() {
  }

}
