import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TarifComponent } from './tarif.component';
import { TarifDetailComponent } from './tarif-detail.component';
import { TarifUpdateComponent } from './tarif-update.component';
import { TarifDeleteDialogComponent } from './tarif-delete-dialog.component';
import { tarifRoute } from './tarif.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(tarifRoute)],
  declarations: [TarifComponent, TarifDetailComponent, TarifUpdateComponent, TarifDeleteDialogComponent],
  entryComponents: [TarifDeleteDialogComponent]
})
export class HpdTarifModule {}
