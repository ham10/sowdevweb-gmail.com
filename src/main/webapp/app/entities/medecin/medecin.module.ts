import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { MedecinComponent } from './medecin.component';
import { MedecinDetailComponent } from './medecin-detail.component';
import { MedecinUpdateComponent } from './medecin-update.component';
import { MedecinDeleteDialogComponent } from './medecin-delete-dialog.component';
import { medecinRoute } from './medecin.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(medecinRoute)],
  declarations: [MedecinComponent, MedecinDetailComponent, MedecinUpdateComponent, MedecinDeleteDialogComponent],
  entryComponents: [MedecinDeleteDialogComponent]
})
export class HpdMedecinModule {}
