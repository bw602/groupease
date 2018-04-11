import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChannelAdminComponent } from './channel-admin/channel-admin.component';
import { JoinRequestListComponent } from './join-request-list/join-request-list.component';
import { UserListComponent } from './user-list/user-list.component';
import { MemberListComponent } from './member-list/member-list.component';

const channelAdminRoutes: Routes = [
  {
    path: '',
    component: ChannelAdminComponent,
    children: [
      {
        path: 'join-requests',
        component: JoinRequestListComponent
      },
      {
        path: 'channel-members',
        component: MemberListComponent
      },
      {
        path: 'users',
        component: UserListComponent
      },
      {
        path: '',
        redirectTo: 'join-requests',
        pathMatch: 'full'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(channelAdminRoutes)],
  exports: [RouterModule]
})
export class ChannelAdminRoutingModule { }
