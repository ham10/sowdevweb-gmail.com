import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { CatChambreComponent } from './cat-chambre.component';
import { CatChambreDetailComponent } from './cat-chambre-detail.component';
import { CatChambreUpdateComponent } from './cat-chambre-update.component';
import { CatChambreDeleteDialogComponent } from './cat-chambre-delete-dialog.component';
import { catChambreRoute } from './cat-chambre.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(catChambreRoute)],
  declarations: [CatChambreComponent, CatChambreDetailComponent, CatChambreUpdateComponent, CatChambreDeleteDialogComponent],
  entryComponents: [CatChambreDeleteDialogComponent]
})
export class HpdCatChambreModule {}
