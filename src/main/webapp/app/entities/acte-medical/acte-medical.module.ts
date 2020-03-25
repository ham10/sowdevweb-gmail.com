import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { ActeMedicalComponent } from './acte-medical.component';
import { ActeMedicalDetailComponent } from './acte-medical-detail.component';
import { ActeMedicalUpdateComponent } from './acte-medical-update.component';
import { ActeMedicalDeleteDialogComponent } from './acte-medical-delete-dialog.component';
import { acteMedicalRoute } from './acte-medical.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(acteMedicalRoute)],
  declarations: [ActeMedicalComponent, ActeMedicalDetailComponent, ActeMedicalUpdateComponent, ActeMedicalDeleteDialogComponent],
  entryComponents: [ActeMedicalDeleteDialogComponent]
})
export class HpdActeMedicalModule {}
