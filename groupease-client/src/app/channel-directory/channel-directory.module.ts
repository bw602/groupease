import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ChannelDirectoryRoutingModule } from './channel-directory-routing.module';
import { ChannelDirectoryComponent } from './channel-directory/channel-directory.component';
import {
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatSnackBarModule,
  MatTooltipModule
} from '@angular/material';
import { ChannelCreatorComponent } from './channel-creator/channel-creator.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    ChannelDirectoryRoutingModule,
    FormsModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatSnackBarModule,
    MatTooltipModule
  ],
  declarations: [ChannelDirectoryComponent, ChannelCreatorComponent]
})
export class ChannelDirectoryModule { }
