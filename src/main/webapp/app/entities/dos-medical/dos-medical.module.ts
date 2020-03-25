import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { DosMedicalComponent } from './dos-medical.component';
import { DosMedicalDetailComponent } from './dos-medical-detail.component';
import { DosMedicalUpdateComponent } from './dos-medical-update.component';
import { DosMedicalDeleteDialogComponent } from './dos-medical-delete-dialog.component';
import { dosMedicalRoute } from './dos-medical.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(dosMedicalRoute)],
  declarations: [DosMedicalComponent, DosMedicalDetailComponent, DosMedicalUpdateComponent, DosMedicalDeleteDialogComponent],
  entryComponents: [DosMedicalDeleteDialogComponent]
})
export class HpdDosMedicalModule {}
