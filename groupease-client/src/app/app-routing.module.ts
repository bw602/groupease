import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {CallbackComponent} from './callback/callback.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {AuthGuard} from './auth/auth.guard';

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
    },
    {
      path: `${pathRoot}/callback`,
      component: CallbackComponent
    },
    {
      path: `${pathRoot}/dashboard`,
      component: DashboardComponent,
      canActivate: [
        AuthGuard
      ]
    }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [AuthGuard]
})
export class AppRoutingModule {}
