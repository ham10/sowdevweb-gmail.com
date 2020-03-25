import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { FicheMedicalComponent } from './fiche-medical.component';
import { FicheMedicalDetailComponent } from './fiche-medical-detail.component';
import { FicheMedicalUpdateComponent } from './fiche-medical-update.component';
import { FicheMedicalDeleteDialogComponent } from './fiche-medical-delete-dialog.component';
import { ficheMedicalRoute } from './fiche-medical.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(ficheMedicalRoute)],
  declarations: [FicheMedicalComponent, FicheMedicalDetailComponent, FicheMedicalUpdateComponent, FicheMedicalDeleteDialogComponent],
  entryComponents: [FicheMedicalDeleteDialogComponent]
})
export class HpdFicheMedicalModule {}
