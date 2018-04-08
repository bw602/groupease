import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChannelDirectoryComponent } from './channel-directory/channel-directory.component';
import { ChannelCreatorComponent } from './channel-creator/channel-creator.component';

const channelDirectoryRoutes: Routes = [
  {
    path: '',
    component: ChannelDirectoryComponent
  },
  {
    path: 'create',
    component: ChannelCreatorComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(channelDirectoryRoutes)],
  exports: [RouterModule]
})
export class ChannelDirectoryRoutingModule { }
