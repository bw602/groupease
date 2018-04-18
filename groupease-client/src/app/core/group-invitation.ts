import { Group } from './group';
import { Member } from './member';

export class GroupInvitation {

  id: number;
  group: Group;
  sender: Member;
  recipient: Member;
  lastUpdate: number;

}
