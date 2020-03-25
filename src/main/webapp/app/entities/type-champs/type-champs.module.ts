import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypeChampsComponent } from './type-champs.component';
import { TypeChampsDetailComponent } from './type-champs-detail.component';
import { TypeChampsUpdateComponent } from './type-champs-update.component';
import { TypeChampsDeleteDialogComponent } from './type-champs-delete-dialog.component';
import { typeChampsRoute } from './type-champs.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typeChampsRoute)],
  declarations: [TypeChampsComponent, TypeChampsDetailComponent, TypeChampsUpdateComponent, TypeChampsDeleteDialogComponent],
  entryComponents: [TypeChampsDeleteDialogComponent]
})
export class HpdTypeChampsModule {}
