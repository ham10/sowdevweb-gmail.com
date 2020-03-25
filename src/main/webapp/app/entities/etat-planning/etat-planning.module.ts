import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { EtatPlanningComponent } from './etat-planning.component';
import { EtatPlanningDetailComponent } from './etat-planning-detail.component';
import { EtatPlanningUpdateComponent } from './etat-planning-update.component';
import { EtatPlanningDeleteDialogComponent } from './etat-planning-delete-dialog.component';
import { etatPlanningRoute } from './etat-planning.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(etatPlanningRoute)],
  declarations: [EtatPlanningComponent, EtatPlanningDetailComponent, EtatPlanningUpdateComponent, EtatPlanningDeleteDialogComponent],
  entryComponents: [EtatPlanningDeleteDialogComponent]
})
export class HpdEtatPlanningModule {}
