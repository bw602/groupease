import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ToolbarComponent } from './toolbar.component';
import {GroupeaseMaterialModule} from '../groupease-material.module';
import {AuthService} from '../auth/auth.service';
import {RouterTestingModule} from '@angular/router/testing';

describe('ToolbarComponent', () => {
  let component: ToolbarComponent;
  let fixture: ComponentFixture<ToolbarComponent>;

  beforeEach(async(() => {

    const authService = jasmine.createSpyObj(
      'AuthService',
      [
        'isAuthenticated'
      ]
    );

    TestBed.configureTestingModule({
      declarations: [
        ToolbarComponent
      ],
      providers: [
        {
          provide: AuthService,
          useValue: authService
        }
      ],
      imports: [
        GroupeaseMaterialModule,
        RouterTestingModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ToolbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
