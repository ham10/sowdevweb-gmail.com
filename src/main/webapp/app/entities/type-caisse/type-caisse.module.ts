import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypeCaisseComponent } from './type-caisse.component';
import { TypeCaisseDetailComponent } from './type-caisse-detail.component';
import { TypeCaisseUpdateComponent } from './type-caisse-update.component';
import { TypeCaisseDeleteDialogComponent } from './type-caisse-delete-dialog.component';
import { typeCaisseRoute } from './type-caisse.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typeCaisseRoute)],
  declarations: [TypeCaisseComponent, TypeCaisseDetailComponent, TypeCaisseUpdateComponent, TypeCaisseDeleteDialogComponent],
  entryComponents: [TypeCaisseDeleteDialogComponent]
})
export class HpdTypeCaisseModule {}
