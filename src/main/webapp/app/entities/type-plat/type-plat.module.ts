import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypePlatComponent } from './type-plat.component';
import { TypePlatDetailComponent } from './type-plat-detail.component';
import { TypePlatUpdateComponent } from './type-plat-update.component';
import { TypePlatDeleteDialogComponent } from './type-plat-delete-dialog.component';
import { typePlatRoute } from './type-plat.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typePlatRoute)],
  declarations: [TypePlatComponent, TypePlatDetailComponent, TypePlatUpdateComponent, TypePlatDeleteDialogComponent],
  entryComponents: [TypePlatDeleteDialogComponent]
})
export class HpdTypePlatModule {}
