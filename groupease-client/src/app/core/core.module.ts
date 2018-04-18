import { NgModule, Optional, SkipSelf } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../auth/auth.service';
import { UserService } from './user.service';
import { ChannelService } from './channel.service';
import { throwIfAlreadyLoaded } from './module-import.guard';
import { ChannelJoinRequestService } from './channel-join-request.service';
import { ChannelInvitationService } from './channel-invitation.service';
import { GroupService } from './group.service';
import { MemberService } from './member.service';
import { GroupInvitationService } from './group-invitation.service';

@NgModule({
  imports: [
    CommonModule
  ],
  providers: [
    AuthService,
    ChannelService,
    UserService,
    ChannelJoinRequestService,
    ChannelInvitationService,
    GroupService,
    MemberService,
    GroupInvitationService
  ]
})
export class CoreModule {

  constructor(
    @Optional() @SkipSelf() parentModule: CoreModule
  ) {
    throwIfAlreadyLoaded(parentModule, 'CoreModule');
  }

}
