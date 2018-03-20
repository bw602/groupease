import {Component, HostBinding, OnInit} from '@angular/core';
import {AuthService} from '../auth/auth.service';

@Component({
  selector: 'app-callback',
  templateUrl: './callback.component.html',
  styleUrls: ['./callback.component.scss']
})
export class CallbackComponent implements OnInit {

  /* Apply groupease-view CSS class to the component element. */
  @HostBinding('class.groupease-view') true;

  constructor(
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.authService.handleAuthentication();
  }

}
