import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ChannelAdminRoutingModule } from './channel-admin-routing.module';
import { ChannelAdminComponent } from './channel-admin/channel-admin.component';
import { JoinRequestListComponent } from './join-request-list/join-request-list.component';
import { UserListComponent } from './user-list/user-list.component';
import { MemberListComponent } from './member-list/member-list.component';
import { MatCardModule, MatIconModule, MatListModule, MatTabsModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    ChannelAdminRoutingModule,
    MatCardModule,
    MatIconModule,
    MatListModule,
    MatTabsModule
  ],
  declarations: [ChannelAdminComponent, JoinRequestListComponent, UserListComponent, MemberListComponent]
})
export class ChannelAdminModule { }
