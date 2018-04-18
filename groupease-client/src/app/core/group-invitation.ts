import { Group } from './group';
import { User } from './user';

export class GroupInvitation {

  id: number;
  group: Group;
  sender: User;
  recipient: User;
  lastUpdate: number;

}
