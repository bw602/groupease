import { User } from './user';
import { Channel } from './channel';

export class Member {

  id: number;
  channel: Channel;
  owner: boolean;
  availability: string;
  goals: string;
  skills: string;
  groupeaseUser: User;
  lastUpdate: number;

}
