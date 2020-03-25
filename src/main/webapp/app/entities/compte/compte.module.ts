import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { CompteComponent } from './compte.component';
import { CompteDetailComponent } from './compte-detail.component';
import { CompteUpdateComponent } from './compte-update.component';
import { CompteDeleteDialogComponent } from './compte-delete-dialog.component';
import { compteRoute } from './compte.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(compteRoute)],
  declarations: [CompteComponent, CompteDetailComponent, CompteUpdateComponent, CompteDeleteDialogComponent],
  entryComponents: [CompteDeleteDialogComponent]
})
export class HpdCompteModule {}
