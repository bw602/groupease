import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChannelAdminComponent } from './channel-admin.component';
import { MatCardModule, MatIconModule, MatTabsModule } from '@angular/material';
import { RouterTestingModule } from '@angular/router/testing';

describe('ChannelAdminComponent', () => {
  let component: ChannelAdminComponent;
  let fixture: ComponentFixture<ChannelAdminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        ChannelAdminComponent
      ],
      imports: [
        MatCardModule,
        MatIconModule,
        MatTabsModule,
        RouterTestingModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChannelAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
