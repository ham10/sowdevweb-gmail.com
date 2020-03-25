import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypeImmoComponent } from './type-immo.component';
import { TypeImmoDetailComponent } from './type-immo-detail.component';
import { TypeImmoUpdateComponent } from './type-immo-update.component';
import { TypeImmoDeleteDialogComponent } from './type-immo-delete-dialog.component';
import { typeImmoRoute } from './type-immo.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typeImmoRoute)],
  declarations: [TypeImmoComponent, TypeImmoDetailComponent, TypeImmoUpdateComponent, TypeImmoDeleteDialogComponent],
  entryComponents: [TypeImmoDeleteDialogComponent]
})
export class HpdTypeImmoModule {}
