import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { EtatImmoComponent } from './etat-immo.component';
import { EtatImmoDetailComponent } from './etat-immo-detail.component';
import { EtatImmoUpdateComponent } from './etat-immo-update.component';
import { EtatImmoDeleteDialogComponent } from './etat-immo-delete-dialog.component';
import { etatImmoRoute } from './etat-immo.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(etatImmoRoute)],
  declarations: [EtatImmoComponent, EtatImmoDetailComponent, EtatImmoUpdateComponent, EtatImmoDeleteDialogComponent],
  entryComponents: [EtatImmoDeleteDialogComponent]
})
export class HpdEtatImmoModule {}
