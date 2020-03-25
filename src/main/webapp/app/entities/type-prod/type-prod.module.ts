import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypeProdComponent } from './type-prod.component';
import { TypeProdDetailComponent } from './type-prod-detail.component';
import { TypeProdUpdateComponent } from './type-prod-update.component';
import { TypeProdDeleteDialogComponent } from './type-prod-delete-dialog.component';
import { typeProdRoute } from './type-prod.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typeProdRoute)],
  declarations: [TypeProdComponent, TypeProdDetailComponent, TypeProdUpdateComponent, TypeProdDeleteDialogComponent],
  entryComponents: [TypeProdDeleteDialogComponent]
})
export class HpdTypeProdModule {}
