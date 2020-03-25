import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { ChapComptaComponent } from './chap-compta.component';
import { ChapComptaDetailComponent } from './chap-compta-detail.component';
import { ChapComptaUpdateComponent } from './chap-compta-update.component';
import { ChapComptaDeleteDialogComponent } from './chap-compta-delete-dialog.component';
import { chapComptaRoute } from './chap-compta.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(chapComptaRoute)],
  declarations: [ChapComptaComponent, ChapComptaDetailComponent, ChapComptaUpdateComponent, ChapComptaDeleteDialogComponent],
  entryComponents: [ChapComptaDeleteDialogComponent]
})
export class HpdChapComptaModule {}
