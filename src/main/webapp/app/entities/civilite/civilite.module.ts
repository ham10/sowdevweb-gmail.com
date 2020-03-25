import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { CiviliteComponent } from './civilite.component';
import { CiviliteDetailComponent } from './civilite-detail.component';
import { CiviliteUpdateComponent } from './civilite-update.component';
import { CiviliteDeleteDialogComponent } from './civilite-delete-dialog.component';
import { civiliteRoute } from './civilite.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(civiliteRoute)],
  declarations: [CiviliteComponent, CiviliteDetailComponent, CiviliteUpdateComponent, CiviliteDeleteDialogComponent],
  entryComponents: [CiviliteDeleteDialogComponent]
})
export class HpdCiviliteModule {}
