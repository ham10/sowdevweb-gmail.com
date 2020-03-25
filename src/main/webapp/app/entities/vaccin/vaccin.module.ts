import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { VaccinComponent } from './vaccin.component';
import { VaccinDetailComponent } from './vaccin-detail.component';
import { VaccinUpdateComponent } from './vaccin-update.component';
import { VaccinDeleteDialogComponent } from './vaccin-delete-dialog.component';
import { vaccinRoute } from './vaccin.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(vaccinRoute)],
  declarations: [VaccinComponent, VaccinDetailComponent, VaccinUpdateComponent, VaccinDeleteDialogComponent],
  entryComponents: [VaccinDeleteDialogComponent]
})
export class HpdVaccinModule {}
