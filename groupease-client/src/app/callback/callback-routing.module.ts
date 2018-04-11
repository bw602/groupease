import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CallbackComponent } from './callback/callback.component';

const callbackRoutes: Routes = [
  {
    path: '',
    component: CallbackComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(callbackRoutes)],
  exports: [RouterModule]
})
export class CallbackRoutingModule { }
