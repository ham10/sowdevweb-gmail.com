import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { EtatRdvComponent } from './etat-rdv.component';
import { EtatRdvDetailComponent } from './etat-rdv-detail.component';
import { EtatRdvUpdateComponent } from './etat-rdv-update.component';
import { EtatRdvDeleteDialogComponent } from './etat-rdv-delete-dialog.component';
import { etatRdvRoute } from './etat-rdv.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(etatRdvRoute)],
  declarations: [EtatRdvComponent, EtatRdvDetailComponent, EtatRdvUpdateComponent, EtatRdvDeleteDialogComponent],
  entryComponents: [EtatRdvDeleteDialogComponent]
})
export class HpdEtatRdvModule {}
