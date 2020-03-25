import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { ModeRegleComponent } from './mode-regle.component';
import { ModeRegleDetailComponent } from './mode-regle-detail.component';
import { ModeRegleUpdateComponent } from './mode-regle-update.component';
import { ModeRegleDeleteDialogComponent } from './mode-regle-delete-dialog.component';
import { modeRegleRoute } from './mode-regle.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(modeRegleRoute)],
  declarations: [ModeRegleComponent, ModeRegleDetailComponent, ModeRegleUpdateComponent, ModeRegleDeleteDialogComponent],
  entryComponents: [ModeRegleDeleteDialogComponent]
})
export class HpdModeRegleModule {}
