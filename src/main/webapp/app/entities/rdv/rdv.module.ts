import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { RDVComponent } from './rdv.component';
import { RDVDetailComponent } from './rdv-detail.component';
import { RDVUpdateComponent } from './rdv-update.component';
import { RDVDeleteDialogComponent } from './rdv-delete-dialog.component';
import { rDVRoute } from './rdv.route';
import { RdvHomeComponent } from './rdv-home/rdv-home.component';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(rDVRoute)],
  declarations: [RDVComponent, RDVDetailComponent, RDVUpdateComponent, RDVDeleteDialogComponent, RdvHomeComponent],
  entryComponents: [RDVDeleteDialogComponent]
})
export class HpdRDVModule {}
