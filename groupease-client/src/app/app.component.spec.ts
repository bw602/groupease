import {async, TestBed} from '@angular/core/testing';
import {AppComponent} from './app.component';
import {RouterTestingModule} from '@angular/router/testing';
import {ToolbarComponent} from './toolbar/toolbar.component';
import {GroupeaseMaterialModule} from './groupease-material.module';

describe('AppComponent', () => {

  const TITLE = 'Groupease';

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        ToolbarComponent
      ],
      imports: [
        GroupeaseMaterialModule,
        RouterTestingModule
      ]
    }).compileComponents();
  }));

  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));

  it(`should have as title 'Groupease'`, async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('Groupease');
  }));

  it('should render app-toolbar element', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('app-toolbar')).toEqual(jasmine.any(Element));
  }));

  it('should render router-outlet element', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('router-outlet')).toEqual(jasmine.any(Element));
  }));

});
