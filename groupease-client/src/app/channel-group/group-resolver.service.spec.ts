import { TestBed, inject } from '@angular/core/testing';

import { GroupResolverService } from './group-resolver.service';
import { GroupService } from '../core/group.service';
import { RouterTestingModule } from '@angular/router/testing';

describe('GroupResolverService', () => {

  let groupService: jasmine.SpyObj<GroupService>;

  beforeEach(() => {

    groupService = jasmine.createSpyObj(
      'GroupService',
      [
        'getGroup'
      ]
    );

    TestBed.configureTestingModule({
      providers: [
        GroupResolverService,
        {
          provide: GroupService,
          useValue: groupService
        }
      ],
      imports: [
        RouterTestingModule
      ]
    });
  });

  it('should be created', inject([GroupResolverService], (service: GroupResolverService) => {
    expect(service).toBeTruthy();
  }));

});
