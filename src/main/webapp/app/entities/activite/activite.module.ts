import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { ActiviteComponent } from './activite.component';
import { ActiviteDetailComponent } from './activite-detail.component';
import { ActiviteUpdateComponent } from './activite-update.component';
import { ActiviteDeleteDialogComponent } from './activite-delete-dialog.component';
import { activiteRoute } from './activite.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(activiteRoute)],
  declarations: [ActiviteComponent, ActiviteDetailComponent, ActiviteUpdateComponent, ActiviteDeleteDialogComponent],
  entryComponents: [ActiviteDeleteDialogComponent]
})
export class HpdActiviteModule {}
