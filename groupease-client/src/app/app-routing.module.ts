import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';

const pathRoot = 'views',
  routes: Routes = [
  {
    path: '',
    redirectTo: `/${pathRoot}/home`,
    pathMatch: 'full'
  },
  {
    path: `${pathRoot}/home`,
    component: HomeComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
