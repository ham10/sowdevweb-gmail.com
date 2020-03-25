import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { EtatBonComComponent } from './etat-bon-com.component';
import { EtatBonComDetailComponent } from './etat-bon-com-detail.component';
import { EtatBonComUpdateComponent } from './etat-bon-com-update.component';
import { EtatBonComDeleteDialogComponent } from './etat-bon-com-delete-dialog.component';
import { etatBonComRoute } from './etat-bon-com.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(etatBonComRoute)],
  declarations: [EtatBonComComponent, EtatBonComDetailComponent, EtatBonComUpdateComponent, EtatBonComDeleteDialogComponent],
  entryComponents: [EtatBonComDeleteDialogComponent]
})
export class HpdEtatBonComModule {}
