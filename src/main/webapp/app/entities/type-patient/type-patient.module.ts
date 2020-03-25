import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypePatientComponent } from './type-patient.component';
import { TypePatientDetailComponent } from './type-patient-detail.component';
import { TypePatientUpdateComponent } from './type-patient-update.component';
import { TypePatientDeleteDialogComponent } from './type-patient-delete-dialog.component';
import { typePatientRoute } from './type-patient.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typePatientRoute)],
  declarations: [TypePatientComponent, TypePatientDetailComponent, TypePatientUpdateComponent, TypePatientDeleteDialogComponent],
  entryComponents: [TypePatientDeleteDialogComponent]
})
export class HpdTypePatientModule {}
