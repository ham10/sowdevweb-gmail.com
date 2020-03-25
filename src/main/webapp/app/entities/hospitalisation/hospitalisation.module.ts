import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { HospitalisationComponent } from './hospitalisation.component';
import { HospitalisationDetailComponent } from './hospitalisation-detail.component';
import { HospitalisationUpdateComponent } from './hospitalisation-update.component';
import { HospitalisationDeleteDialogComponent } from './hospitalisation-delete-dialog.component';
import { hospitalisationRoute } from './hospitalisation.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(hospitalisationRoute)],
  declarations: [
    HospitalisationComponent,
    HospitalisationDetailComponent,
    HospitalisationUpdateComponent,
    HospitalisationDeleteDialogComponent
  ],
  entryComponents: [HospitalisationDeleteDialogComponent]
})
export class HpdHospitalisationModule {}
