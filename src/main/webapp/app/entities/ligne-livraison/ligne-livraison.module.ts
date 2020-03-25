import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { LigneLivraisonComponent } from './ligne-livraison.component';
import { LigneLivraisonDetailComponent } from './ligne-livraison-detail.component';
import { LigneLivraisonUpdateComponent } from './ligne-livraison-update.component';
import { LigneLivraisonDeleteDialogComponent } from './ligne-livraison-delete-dialog.component';
import { ligneLivraisonRoute } from './ligne-livraison.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(ligneLivraisonRoute)],
  declarations: [
    LigneLivraisonComponent,
    LigneLivraisonDetailComponent,
    LigneLivraisonUpdateComponent,
    LigneLivraisonDeleteDialogComponent
  ],
  entryComponents: [LigneLivraisonDeleteDialogComponent]
})
export class HpdLigneLivraisonModule {}
