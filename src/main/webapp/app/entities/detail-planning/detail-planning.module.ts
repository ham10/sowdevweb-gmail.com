import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { DetailPlanningComponent } from './detail-planning.component';
import { DetailPlanningDetailComponent } from './detail-planning-detail.component';
import { DetailPlanningUpdateComponent } from './detail-planning-update.component';
import { DetailPlanningDeleteDialogComponent } from './detail-planning-delete-dialog.component';
import { detailPlanningRoute } from './detail-planning.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(detailPlanningRoute)],
  declarations: [
    DetailPlanningComponent,
    DetailPlanningDetailComponent,
    DetailPlanningUpdateComponent,
    DetailPlanningDeleteDialogComponent
  ],
  entryComponents: [DetailPlanningDeleteDialogComponent]
})
export class HpdDetailPlanningModule {}
