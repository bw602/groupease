import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CallbackRoutingModule } from './callback-routing.module';
import { CallbackComponent } from './callback/callback.component';
import { MatProgressSpinnerModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    CallbackRoutingModule,
    MatProgressSpinnerModule
  ],
  declarations: [CallbackComponent]
})
export class CallbackModule { }
