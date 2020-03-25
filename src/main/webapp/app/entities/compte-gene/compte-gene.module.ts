import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { CompteGeneComponent } from './compte-gene.component';
import { CompteGeneDetailComponent } from './compte-gene-detail.component';
import { CompteGeneUpdateComponent } from './compte-gene-update.component';
import { CompteGeneDeleteDialogComponent } from './compte-gene-delete-dialog.component';
import { compteGeneRoute } from './compte-gene.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(compteGeneRoute)],
  declarations: [CompteGeneComponent, CompteGeneDetailComponent, CompteGeneUpdateComponent, CompteGeneDeleteDialogComponent],
  entryComponents: [CompteGeneDeleteDialogComponent]
})
export class HpdCompteGeneModule {}
