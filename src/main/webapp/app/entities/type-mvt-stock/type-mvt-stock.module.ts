import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypeMvtStockComponent } from './type-mvt-stock.component';
import { TypeMvtStockDetailComponent } from './type-mvt-stock-detail.component';
import { TypeMvtStockUpdateComponent } from './type-mvt-stock-update.component';
import { TypeMvtStockDeleteDialogComponent } from './type-mvt-stock-delete-dialog.component';
import { typeMvtStockRoute } from './type-mvt-stock.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typeMvtStockRoute)],
  declarations: [TypeMvtStockComponent, TypeMvtStockDetailComponent, TypeMvtStockUpdateComponent, TypeMvtStockDeleteDialogComponent],
  entryComponents: [TypeMvtStockDeleteDialogComponent]
})
export class HpdTypeMvtStockModule {}
