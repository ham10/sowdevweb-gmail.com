import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { EtatCaisseComponent } from './etat-caisse.component';
import { EtatCaisseDetailComponent } from './etat-caisse-detail.component';
import { EtatCaisseUpdateComponent } from './etat-caisse-update.component';
import { EtatCaisseDeleteDialogComponent } from './etat-caisse-delete-dialog.component';
import { etatCaisseRoute } from './etat-caisse.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(etatCaisseRoute)],
  declarations: [EtatCaisseComponent, EtatCaisseDetailComponent, EtatCaisseUpdateComponent, EtatCaisseDeleteDialogComponent],
  entryComponents: [EtatCaisseDeleteDialogComponent]
})
export class HpdEtatCaisseModule {}
