import { NgModule } from '@angular/core';
import { ExtraOptions, PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth/auth.guard';

const pathRoot = 'views',
  config: ExtraOptions = {
    preloadingStrategy: PreloadAllModules
  },
  routes: Routes = [
    {
      path: `${pathRoot}/home`,
      loadChildren: 'app/home/home.module#HomeModule'
    },
    {
      path: `${pathRoot}/callback`,
      loadChildren: 'app/callback/callback.module#CallbackModule'
    },
    {
      path: `${pathRoot}/dashboard`,
      loadChildren: 'app/dashboard/dashboard.module#DashboardModule',
      canLoad: [
        AuthGuard
      ]
    },
    {
      path: `${pathRoot}/channel-directory`,
      loadChildren: 'app/channel-directory/channel-directory.module#ChannelDirectoryModule',
      canLoad: [
        AuthGuard
      ]
    },
    {
      path: `${pathRoot}/channels/:id`,
      loadChildren: 'app/channel/channel.module#ChannelModule',
      canLoad: [
        AuthGuard
      ]
    },
    {
      path: '**',
      redirectTo: `/${pathRoot}/home`
    }
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      routes,
      config
    )
  ],
  exports: [RouterModule],
  providers: [AuthGuard]
})
export class AppRoutingModule {}
