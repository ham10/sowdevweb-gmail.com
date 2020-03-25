import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypePlateauComponent } from './type-plateau.component';
import { TypePlateauDetailComponent } from './type-plateau-detail.component';
import { TypePlateauUpdateComponent } from './type-plateau-update.component';
import { TypePlateauDeleteDialogComponent } from './type-plateau-delete-dialog.component';
import { typePlateauRoute } from './type-plateau.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typePlateauRoute)],
  declarations: [TypePlateauComponent, TypePlateauDetailComponent, TypePlateauUpdateComponent, TypePlateauDeleteDialogComponent],
  entryComponents: [TypePlateauDeleteDialogComponent]
})
export class HpdTypePlateauModule {}
