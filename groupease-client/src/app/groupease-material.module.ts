import { NgModule } from '@angular/core';
import {
  MatButtonModule, MatIconModule, MatListModule, MatProgressSpinnerModule, MatSidenavModule,
  MatToolbarModule
} from '@angular/material';

const materialModules = [
  MatButtonModule,
  MatIconModule,
  MatListModule,
  MatProgressSpinnerModule,
  MatSidenavModule,
  MatToolbarModule
];

@NgModule({
  imports: materialModules,
  exports: materialModules
})
export class GroupeaseMaterialModule { }
