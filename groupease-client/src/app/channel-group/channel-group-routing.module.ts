import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChannelGroupDirectoryComponent } from './channel-group-directory/channel-group-directory.component';

const channelGroupRoutes: Routes = [
  {
    path: '',
    component: ChannelGroupDirectoryComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(channelGroupRoutes)],
  exports: [RouterModule]
})
export class ChannelGroupRoutingModule { }
