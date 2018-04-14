import { Component, HostBinding, OnInit } from '@angular/core';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  /* Apply CSS class to the component element. */
  @HostBinding('class.groupease-root') true;

  constructor(
    public authService: AuthService
  ) { }

  ngOnInit() {
  }

}
