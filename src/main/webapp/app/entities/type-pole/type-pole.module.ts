import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypePoleComponent } from './type-pole.component';
import { TypePoleDetailComponent } from './type-pole-detail.component';
import { TypePoleUpdateComponent } from './type-pole-update.component';
import { TypePoleDeleteDialogComponent } from './type-pole-delete-dialog.component';
import { typePoleRoute } from './type-pole.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typePoleRoute)],
  declarations: [TypePoleComponent, TypePoleDetailComponent, TypePoleUpdateComponent, TypePoleDeleteDialogComponent],
  entryComponents: [TypePoleDeleteDialogComponent]
})
export class HpdTypePoleModule {}
