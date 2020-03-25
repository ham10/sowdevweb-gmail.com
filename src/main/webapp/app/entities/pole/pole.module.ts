import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { PoleComponent } from './pole.component';
import { PoleDetailComponent } from './pole-detail.component';
import { PoleUpdateComponent } from './pole-update.component';
import { PoleDeleteDialogComponent } from './pole-delete-dialog.component';
import { poleRoute } from './pole.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(poleRoute)],
  declarations: [PoleComponent, PoleDetailComponent, PoleUpdateComponent, PoleDeleteDialogComponent],
  entryComponents: [PoleDeleteDialogComponent]
})
export class HpdPoleModule {}
