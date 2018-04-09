import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChannelMemberDirectoryComponent } from './channel-member-directory/channel-member-directory.component';

const channelMemberRoutes: Routes = [
  {
    path: '',
    component: ChannelMemberDirectoryComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(channelMemberRoutes)],
  exports: [RouterModule]
})
export class ChannelMemberRoutingModule { }
