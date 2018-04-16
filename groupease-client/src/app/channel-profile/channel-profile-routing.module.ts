import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProfileHomeComponent } from './profile-home/profile-home.component';
import { ProfileFormComponent } from './profile-form/profile-form.component';
import { MemberInvitationsComponent } from './member-invitations/member-invitations.component';

const profileRoutes: Routes = [
  {
    path: '',
    component: ProfileHomeComponent,
    children: [
      {
        path: 'edit',
        component: ProfileFormComponent
      },
      {
        path: 'group-invitations',
        component: MemberInvitationsComponent
      },
      {
        path: '',
        redirectTo: 'edit',
        pathMatch: 'full'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(profileRoutes)],
  exports: [RouterModule]
})
export class ChannelProfileRoutingModule { }
