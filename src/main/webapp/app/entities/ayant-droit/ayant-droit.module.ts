import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { AyantDroitComponent } from './ayant-droit.component';
import { AyantDroitDetailComponent } from './ayant-droit-detail.component';
import { AyantDroitUpdateComponent } from './ayant-droit-update.component';
import { AyantDroitDeleteDialogComponent } from './ayant-droit-delete-dialog.component';
import { ayantDroitRoute } from './ayant-droit.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(ayantDroitRoute)],
  declarations: [AyantDroitComponent, AyantDroitDetailComponent, AyantDroitUpdateComponent, AyantDroitDeleteDialogComponent],
  entryComponents: [AyantDroitDeleteDialogComponent]
})
export class HpdAyantDroitModule {}
