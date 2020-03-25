import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { EtatFactureComponent } from './etat-facture.component';
import { EtatFactureDetailComponent } from './etat-facture-detail.component';
import { EtatFactureUpdateComponent } from './etat-facture-update.component';
import { EtatFactureDeleteDialogComponent } from './etat-facture-delete-dialog.component';
import { etatFactureRoute } from './etat-facture.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(etatFactureRoute)],
  declarations: [EtatFactureComponent, EtatFactureDetailComponent, EtatFactureUpdateComponent, EtatFactureDeleteDialogComponent],
  entryComponents: [EtatFactureDeleteDialogComponent]
})
export class HpdEtatFactureModule {}
