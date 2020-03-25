import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { LitComponent } from './lit.component';
import { LitDetailComponent } from './lit-detail.component';
import { LitUpdateComponent } from './lit-update.component';
import { LitDeleteDialogComponent } from './lit-delete-dialog.component';
import { litRoute } from './lit.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(litRoute)],
  declarations: [LitComponent, LitDetailComponent, LitUpdateComponent, LitDeleteDialogComponent],
  entryComponents: [LitDeleteDialogComponent]
})
export class HpdLitModule {}
