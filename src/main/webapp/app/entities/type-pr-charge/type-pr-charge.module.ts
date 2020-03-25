import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypePrChargeComponent } from './type-pr-charge.component';
import { TypePrChargeDetailComponent } from './type-pr-charge-detail.component';
import { TypePrChargeUpdateComponent } from './type-pr-charge-update.component';
import { TypePrChargeDeleteDialogComponent } from './type-pr-charge-delete-dialog.component';
import { typePrChargeRoute } from './type-pr-charge.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typePrChargeRoute)],
  declarations: [TypePrChargeComponent, TypePrChargeDetailComponent, TypePrChargeUpdateComponent, TypePrChargeDeleteDialogComponent],
  entryComponents: [TypePrChargeDeleteDialogComponent]
})
export class HpdTypePrChargeModule {}
