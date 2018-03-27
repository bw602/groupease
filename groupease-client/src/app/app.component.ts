import {Component, HostBinding} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  /* Apply groupease-view CSS class to the component element. */
  @HostBinding('class.groupease-view') true;

}
