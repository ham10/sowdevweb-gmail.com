import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypeFournisComponent } from './type-fournis.component';
import { TypeFournisDetailComponent } from './type-fournis-detail.component';
import { TypeFournisUpdateComponent } from './type-fournis-update.component';
import { TypeFournisDeleteDialogComponent } from './type-fournis-delete-dialog.component';
import { typeFournisRoute } from './type-fournis.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typeFournisRoute)],
  declarations: [TypeFournisComponent, TypeFournisDetailComponent, TypeFournisUpdateComponent, TypeFournisDeleteDialogComponent],
  entryComponents: [TypeFournisDeleteDialogComponent]
})
export class HpdTypeFournisModule {}
