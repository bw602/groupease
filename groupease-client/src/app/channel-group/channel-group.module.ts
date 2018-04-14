import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ChannelGroupRoutingModule } from './channel-group-routing.module';
import { ChannelGroupDirectoryComponent } from './channel-group-directory/channel-group-directory.component';
import {
  MatButtonModule,
  MatCardModule,
  MatChipsModule,
  MatExpansionModule,
  MatFormFieldModule,
  MatIconModule, MatInputModule, MatSnackBarModule, MatTabsModule,
  MatTooltipModule
} from '@angular/material';
import { GroupCreatorComponent } from './group-creator/group-creator.component';
import { FormsModule } from '@angular/forms';
import { GroupDetailsComponent } from './group-details/group-details.component';
import { GroupResolverService } from './group-resolver.service';
import { GroupDetailsHomeComponent } from './group-details-home/group-details-home.component';

@NgModule({
  imports: [
    CommonModule,
    ChannelGroupRoutingModule,
    FormsModule,
    MatButtonModule,
    MatCardModule,
    MatChipsModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSnackBarModule,
    MatTabsModule,
    MatTooltipModule
  ],
  declarations: [ChannelGroupDirectoryComponent, GroupCreatorComponent, GroupDetailsComponent, GroupDetailsHomeComponent],
  providers: [GroupResolverService]
})
export class ChannelGroupModule { }
