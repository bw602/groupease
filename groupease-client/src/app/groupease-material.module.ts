import { NgModule } from '@angular/core';
import {MatButtonModule, MatSidenavModule, MatToolbarModule} from '@angular/material';

const materialModules = [
  MatButtonModule,
  MatSidenavModule,
  MatToolbarModule
];

@NgModule({
  imports: materialModules,
  exports: materialModules
})
export class GroupeaseMaterialModule { }
