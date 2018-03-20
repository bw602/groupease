import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardComponent } from './dashboard.component';
import {ChannelService} from '../channel.service';
import {GroupeaseMaterialModule} from '../groupease-material.module';
import {of} from 'rxjs/observable/of';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;
  let channelService: jasmine.SpyObj<ChannelService>;

  beforeEach(async(() => {

    channelService = jasmine.createSpyObj(
      'ChannelService',
      [
        'listAll'
      ]
    );

    TestBed.configureTestingModule({
      declarations: [
        DashboardComponent
      ],
      providers: [
        {
          provide: ChannelService,
          useValue: channelService
        }
      ],
      imports: [
        GroupeaseMaterialModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    channelService.listAll.and.returnValue(of([]));
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
