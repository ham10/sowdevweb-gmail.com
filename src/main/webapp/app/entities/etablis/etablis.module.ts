import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { EtablisComponent } from './etablis.component';
import { EtablisDetailComponent } from './etablis-detail.component';
import { EtablisUpdateComponent } from './etablis-update.component';
import { EtablisDeleteDialogComponent } from './etablis-delete-dialog.component';
import { etablisRoute } from './etablis.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(etablisRoute)],
  declarations: [EtablisComponent, EtablisDetailComponent, EtablisUpdateComponent, EtablisDeleteDialogComponent],
  entryComponents: [EtablisDeleteDialogComponent]
})
export class HpdEtablisModule {}
