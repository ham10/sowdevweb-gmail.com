import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { EtatOperationComponent } from './etat-operation.component';
import { EtatOperationDetailComponent } from './etat-operation-detail.component';
import { EtatOperationUpdateComponent } from './etat-operation-update.component';
import { EtatOperationDeleteDialogComponent } from './etat-operation-delete-dialog.component';
import { etatOperationRoute } from './etat-operation.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(etatOperationRoute)],
  declarations: [EtatOperationComponent, EtatOperationDetailComponent, EtatOperationUpdateComponent, EtatOperationDeleteDialogComponent],
  entryComponents: [EtatOperationDeleteDialogComponent]
})
export class HpdEtatOperationModule {}
