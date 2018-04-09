import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChannelMemberDirectoryComponent } from './channel-member-directory.component';

describe('ChannelMemberDirectoryComponent', () => {
  let component: ChannelMemberDirectoryComponent;
  let fixture: ComponentFixture<ChannelMemberDirectoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChannelMemberDirectoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChannelMemberDirectoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
