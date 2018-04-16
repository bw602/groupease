import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChannelComponent } from './channel/channel.component';
import { ChannelHomeComponent } from './channel-home/channel-home.component';
import { ChannelResolverService } from './channel-resolver.service';
import { AuthGuard } from '../auth/auth.guard';

const channelRoutes: Routes = [
  {
    path: '',
    component: ChannelComponent,
    children: [
      {
        path: 'home',
        component: ChannelHomeComponent
      },
      {
        path: 'profile',
        loadChildren: 'app/channel-profile/channel-profile.module#ChannelProfileModule',
        canLoad: [
          AuthGuard
        ]
      },
      {
        path: 'members',
        loadChildren: 'app/channel-member/channel-member.module#ChannelMemberModule',
        canLoad: [
          AuthGuard
        ]
      },
      {
        path: 'groups',
        loadChildren: 'app/channel-group/channel-group.module#ChannelGroupModule',
        canLoad: [
          AuthGuard
        ]
      },
      {
        path: 'admin',
        loadChildren: 'app/channel-admin/channel-admin.module#ChannelAdminModule',
        canLoad: [
          AuthGuard
        ]
      },
      {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
      }
    ],
    resolve: {
      channel: ChannelResolverService
    }
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(channelRoutes)
  ],
  exports: [
    RouterModule
  ],
  providers: [
    ChannelResolverService
  ]
})
export class ChannelRoutingModule { }
