import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { EcheancierComponent } from './echeancier.component';
import { EcheancierDetailComponent } from './echeancier-detail.component';
import { EcheancierUpdateComponent } from './echeancier-update.component';
import { EcheancierDeleteDialogComponent } from './echeancier-delete-dialog.component';
import { echeancierRoute } from './echeancier.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(echeancierRoute)],
  declarations: [EcheancierComponent, EcheancierDetailComponent, EcheancierUpdateComponent, EcheancierDeleteDialogComponent],
  entryComponents: [EcheancierDeleteDialogComponent]
})
export class HpdEcheancierModule {}
