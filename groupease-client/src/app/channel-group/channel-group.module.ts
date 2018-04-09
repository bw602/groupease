import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ChannelGroupRoutingModule } from './channel-group-routing.module';
import { ChannelGroupDirectoryComponent } from './channel-group-directory/channel-group-directory.component';

@NgModule({
  imports: [
    CommonModule,
    ChannelGroupRoutingModule
  ],
  declarations: [ChannelGroupDirectoryComponent]
})
export class ChannelGroupModule { }
