import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ChannelProfileRoutingModule } from './channel-profile-routing.module';
import { ProfileHomeComponent } from './profile-home/profile-home.component';
import { ProfileFormComponent } from './profile-form/profile-form.component';
import { MemberInvitationsComponent } from './member-invitations/member-invitations.component';
import {
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatListModule, MatSnackBarModule,
  MatTabsModule
} from '@angular/material';
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    ChannelProfileRoutingModule,
    FormsModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatSnackBarModule,
    MatTabsModule
  ],
  declarations: [ProfileHomeComponent, ProfileFormComponent, MemberInvitationsComponent]
})
export class ChannelProfileModule { }
