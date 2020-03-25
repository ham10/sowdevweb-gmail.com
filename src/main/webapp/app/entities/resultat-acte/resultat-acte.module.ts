import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { ResultatActeComponent } from './resultat-acte.component';
import { ResultatActeDetailComponent } from './resultat-acte-detail.component';
import { ResultatActeUpdateComponent } from './resultat-acte-update.component';
import { ResultatActeDeleteDialogComponent } from './resultat-acte-delete-dialog.component';
import { resultatActeRoute } from './resultat-acte.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(resultatActeRoute)],
  declarations: [ResultatActeComponent, ResultatActeDetailComponent, ResultatActeUpdateComponent, ResultatActeDeleteDialogComponent],
  entryComponents: [ResultatActeDeleteDialogComponent]
})
export class HpdResultatActeModule {}
