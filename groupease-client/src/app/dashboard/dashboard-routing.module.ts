import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ChannelListComponent } from './channel-list/channel-list.component';
import { InvitationListComponent } from './invitation-list/invitation-list.component';

const dashboardRoutes: Routes = [
  {
    path: '',
    component: DashboardComponent,
    children: [
      {
        path: 'channels',
        component: ChannelListComponent
      },
      {
        path: 'invitations',
        component: InvitationListComponent
      },
      {
        path: '',
        redirectTo: 'channels',
        pathMatch: 'full'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(dashboardRoutes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
