import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChannelNavigationComponent } from './channel-navigation.component';
import { MatIconModule, MatListModule } from '@angular/material';

describe('ChannelNavigationComponent', () => {
  let component: ChannelNavigationComponent;
  let fixture: ComponentFixture<ChannelNavigationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        ChannelNavigationComponent
      ],
      imports: [
        MatIconModule,
        MatListModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChannelNavigationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
