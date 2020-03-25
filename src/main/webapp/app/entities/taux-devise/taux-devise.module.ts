import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TauxDeviseComponent } from './taux-devise.component';
import { TauxDeviseDetailComponent } from './taux-devise-detail.component';
import { TauxDeviseUpdateComponent } from './taux-devise-update.component';
import { TauxDeviseDeleteDialogComponent } from './taux-devise-delete-dialog.component';
import { tauxDeviseRoute } from './taux-devise.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(tauxDeviseRoute)],
  declarations: [TauxDeviseComponent, TauxDeviseDetailComponent, TauxDeviseUpdateComponent, TauxDeviseDeleteDialogComponent],
  entryComponents: [TauxDeviseDeleteDialogComponent]
})
export class HpdTauxDeviseModule {}
