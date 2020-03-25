import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { InventaireComponent } from './inventaire.component';
import { InventaireDetailComponent } from './inventaire-detail.component';
import { InventaireUpdateComponent } from './inventaire-update.component';
import { InventaireDeleteDialogComponent } from './inventaire-delete-dialog.component';
import { inventaireRoute } from './inventaire.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(inventaireRoute)],
  declarations: [InventaireComponent, InventaireDetailComponent, InventaireUpdateComponent, InventaireDeleteDialogComponent],
  entryComponents: [InventaireDeleteDialogComponent]
})
export class HpdInventaireModule {}
