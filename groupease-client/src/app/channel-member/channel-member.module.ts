import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ChannelMemberRoutingModule } from './channel-member-routing.module';
import { ChannelMemberDirectoryComponent } from './channel-member-directory/channel-member-directory.component';

@NgModule({
  imports: [
    CommonModule,
    ChannelMemberRoutingModule
  ],
  declarations: [ChannelMemberDirectoryComponent]
})
export class ChannelMemberModule { }
