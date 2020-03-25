import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { NatureOpComponent } from './nature-op.component';
import { NatureOpDetailComponent } from './nature-op-detail.component';
import { NatureOpUpdateComponent } from './nature-op-update.component';
import { NatureOpDeleteDialogComponent } from './nature-op-delete-dialog.component';
import { natureOpRoute } from './nature-op.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(natureOpRoute)],
  declarations: [NatureOpComponent, NatureOpDetailComponent, NatureOpUpdateComponent, NatureOpDeleteDialogComponent],
  entryComponents: [NatureOpDeleteDialogComponent]
})
export class HpdNatureOpModule {}
