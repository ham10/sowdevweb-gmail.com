import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypeTarifComponent } from './type-tarif.component';
import { TypeTarifDetailComponent } from './type-tarif-detail.component';
import { TypeTarifUpdateComponent } from './type-tarif-update.component';
import { TypeTarifDeleteDialogComponent } from './type-tarif-delete-dialog.component';
import { typeTarifRoute } from './type-tarif.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typeTarifRoute)],
  declarations: [TypeTarifComponent, TypeTarifDetailComponent, TypeTarifUpdateComponent, TypeTarifDeleteDialogComponent],
  entryComponents: [TypeTarifDeleteDialogComponent]
})
export class HpdTypeTarifModule {}
