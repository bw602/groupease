import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { MatButtonModule, MatCardModule, MatIconModule, MatListModule, MatTabsModule, MatTooltipModule } from '@angular/material';
import { InvitationListComponent } from './invitation-list/invitation-list.component';
import { ChannelListComponent } from './channel-list/channel-list.component';

@NgModule({
  imports: [
    CommonModule,
    DashboardRoutingModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatListModule,
    MatTabsModule,
    MatTooltipModule
  ],
  declarations: [DashboardComponent, InvitationListComponent, ChannelListComponent]
})
export class DashboardModule { }
