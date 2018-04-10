import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardComponent } from './dashboard.component';
import { MatButtonModule, MatCardModule, MatIconModule, MatTabsModule, MatTooltipModule } from '@angular/material';
import { RouterTestingModule } from '@angular/router/testing';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;

  beforeEach(async(() => {

    TestBed.configureTestingModule({
      declarations: [
        DashboardComponent
      ],
      imports: [
        MatButtonModule,
        MatCardModule,
        MatIconModule,
        MatTabsModule,
        MatTooltipModule,
        RouterTestingModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
