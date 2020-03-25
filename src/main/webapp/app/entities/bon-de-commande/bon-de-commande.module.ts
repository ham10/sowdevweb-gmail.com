import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { BonDeCommandeComponent } from './bon-de-commande.component';
import { BonDeCommandeDetailComponent } from './bon-de-commande-detail.component';
import { BonDeCommandeUpdateComponent } from './bon-de-commande-update.component';
import { BonDeCommandeDeleteDialogComponent } from './bon-de-commande-delete-dialog.component';
import { bonDeCommandeRoute } from './bon-de-commande.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(bonDeCommandeRoute)],
  declarations: [BonDeCommandeComponent, BonDeCommandeDetailComponent, BonDeCommandeUpdateComponent, BonDeCommandeDeleteDialogComponent],
  entryComponents: [BonDeCommandeDeleteDialogComponent]
})
export class HpdBonDeCommandeModule {}
