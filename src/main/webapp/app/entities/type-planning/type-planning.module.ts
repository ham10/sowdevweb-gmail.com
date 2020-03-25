import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypePlanningComponent } from './type-planning.component';
import { TypePlanningDetailComponent } from './type-planning-detail.component';
import { TypePlanningUpdateComponent } from './type-planning-update.component';
import { TypePlanningDeleteDialogComponent } from './type-planning-delete-dialog.component';
import { typePlanningRoute } from './type-planning.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typePlanningRoute)],
  declarations: [TypePlanningComponent, TypePlanningDetailComponent, TypePlanningUpdateComponent, TypePlanningDeleteDialogComponent],
  entryComponents: [TypePlanningDeleteDialogComponent]
})
export class HpdTypePlanningModule {}
