import { User } from './user';
import { Channel } from './channel';

export class ChannelInvitation {

  id: number;

  recipient: User;

  sentBy: User;

  channel: Channel;

  lastUpdate: number;

}
