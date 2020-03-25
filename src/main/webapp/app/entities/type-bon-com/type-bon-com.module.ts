import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypeBonComComponent } from './type-bon-com.component';
import { TypeBonComDetailComponent } from './type-bon-com-detail.component';
import { TypeBonComUpdateComponent } from './type-bon-com-update.component';
import { TypeBonComDeleteDialogComponent } from './type-bon-com-delete-dialog.component';
import { typeBonComRoute } from './type-bon-com.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typeBonComRoute)],
  declarations: [TypeBonComComponent, TypeBonComDetailComponent, TypeBonComUpdateComponent, TypeBonComDeleteDialogComponent],
  entryComponents: [TypeBonComDeleteDialogComponent]
})
export class HpdTypeBonComModule {}
