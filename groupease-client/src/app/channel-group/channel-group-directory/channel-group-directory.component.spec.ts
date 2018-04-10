import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChannelGroupDirectoryComponent } from './channel-group-directory.component';

describe('ChannelGroupDirectoryComponent', () => {
  let component: ChannelGroupDirectoryComponent;
  let fixture: ComponentFixture<ChannelGroupDirectoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChannelGroupDirectoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChannelGroupDirectoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
