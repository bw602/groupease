import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChannelDirectoryComponent } from './channel-directory/channel-directory.component';

const routes: Routes = [
  {
    path: '',
    component: ChannelDirectoryComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ChannelDirectoryRoutingModule { }
