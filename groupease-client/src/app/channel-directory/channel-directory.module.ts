import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ChannelDirectoryRoutingModule } from './channel-directory-routing.module';
import { ChannelDirectoryComponent } from './channel-directory/channel-directory.component';
import { MatCardModule, MatIconModule, MatListModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    ChannelDirectoryRoutingModule,
    MatCardModule,
    MatIconModule,
    MatListModule
  ],
  declarations: [ChannelDirectoryComponent]
})
export class ChannelDirectoryModule { }
