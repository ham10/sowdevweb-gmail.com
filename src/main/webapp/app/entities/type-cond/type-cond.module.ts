import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypeCondComponent } from './type-cond.component';
import { TypeCondDetailComponent } from './type-cond-detail.component';
import { TypeCondUpdateComponent } from './type-cond-update.component';
import { TypeCondDeleteDialogComponent } from './type-cond-delete-dialog.component';
import { typeCondRoute } from './type-cond.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typeCondRoute)],
  declarations: [TypeCondComponent, TypeCondDetailComponent, TypeCondUpdateComponent, TypeCondDeleteDialogComponent],
  entryComponents: [TypeCondDeleteDialogComponent]
})
export class HpdTypeCondModule {}
