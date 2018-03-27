import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { HomeComponent } from './home/home.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { GroupeaseMaterialModule } from './groupease-material.module';
import { ToolbarComponent } from './toolbar/toolbar.component';
import { CallbackComponent } from './callback/callback.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import {HttpClientModule} from '@angular/common/http';
import { ChannelService } from './channel.service';
import { AuthService } from './auth/auth.service';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ToolbarComponent,
    CallbackComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    GroupeaseMaterialModule,
    HttpClientModule
  ],
  providers: [
    ChannelService,
    AuthService
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule { }
