import { Channel } from './channel';
import { User } from './user';

export class ChannelJoinRequest {

  id: number;

  channel: Channel;

  requestor: User;

  comments: string;

  lastUpdate: number;

}
