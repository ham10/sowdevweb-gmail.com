import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { JourComponent } from './jour.component';
import { JourDetailComponent } from './jour-detail.component';
import { JourUpdateComponent } from './jour-update.component';
import { JourDeleteDialogComponent } from './jour-delete-dialog.component';
import { jourRoute } from './jour.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(jourRoute)],
  declarations: [JourComponent, JourDetailComponent, JourUpdateComponent, JourDeleteDialogComponent],
  entryComponents: [JourDeleteDialogComponent]
})
export class HpdJourModule {}
