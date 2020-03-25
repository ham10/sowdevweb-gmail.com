import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypeSoinsComponent } from './type-soins.component';
import { TypeSoinsDetailComponent } from './type-soins-detail.component';
import { TypeSoinsUpdateComponent } from './type-soins-update.component';
import { TypeSoinsDeleteDialogComponent } from './type-soins-delete-dialog.component';
import { typeSoinsRoute } from './type-soins.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typeSoinsRoute)],
  declarations: [TypeSoinsComponent, TypeSoinsDetailComponent, TypeSoinsUpdateComponent, TypeSoinsDeleteDialogComponent],
  entryComponents: [TypeSoinsDeleteDialogComponent]
})
export class HpdTypeSoinsModule {}
