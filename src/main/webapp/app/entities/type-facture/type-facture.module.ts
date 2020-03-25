import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypeFactureComponent } from './type-facture.component';
import { TypeFactureDetailComponent } from './type-facture-detail.component';
import { TypeFactureUpdateComponent } from './type-facture-update.component';
import { TypeFactureDeleteDialogComponent } from './type-facture-delete-dialog.component';
import { typeFactureRoute } from './type-facture.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typeFactureRoute)],
  declarations: [TypeFactureComponent, TypeFactureDetailComponent, TypeFactureUpdateComponent, TypeFactureDeleteDialogComponent],
  entryComponents: [TypeFactureDeleteDialogComponent]
})
export class HpdTypeFactureModule {}
