import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { BonLivraisonComponent } from './bon-livraison.component';
import { BonLivraisonDetailComponent } from './bon-livraison-detail.component';
import { BonLivraisonUpdateComponent } from './bon-livraison-update.component';
import { BonLivraisonDeleteDialogComponent } from './bon-livraison-delete-dialog.component';
import { bonLivraisonRoute } from './bon-livraison.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(bonLivraisonRoute)],
  declarations: [BonLivraisonComponent, BonLivraisonDetailComponent, BonLivraisonUpdateComponent, BonLivraisonDeleteDialogComponent],
  entryComponents: [BonLivraisonDeleteDialogComponent]
})
export class HpdBonLivraisonModule {}
