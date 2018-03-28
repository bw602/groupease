import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CallbackComponent } from './callback.component';
import {GroupeaseMaterialModule} from '../groupease-material.module';
import {AuthService} from '../auth/auth.service';

describe('CallbackComponent', () => {
  let component: CallbackComponent;
  let fixture: ComponentFixture<CallbackComponent>;

  beforeEach(async(() => {

    const authService = jasmine.createSpyObj(
      'AuthService',
      [
        'handleAuthentication'
      ]
    );

    TestBed.configureTestingModule({
      declarations: [ CallbackComponent ],
      providers: [
        {
          provide: AuthService,
          useValue: authService
        }
      ],
      imports: [
        GroupeaseMaterialModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CallbackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
