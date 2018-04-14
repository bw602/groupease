import { Member } from './member';

export class Group {

  id: number;
  channelId: number;
  name: string;
  description: string;
  full = false;
  members: Member[] = [];
  lastUpdate: number;

}
