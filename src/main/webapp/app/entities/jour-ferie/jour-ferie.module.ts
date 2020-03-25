import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { JourFerieComponent } from './jour-ferie.component';
import { JourFerieDetailComponent } from './jour-ferie-detail.component';
import { JourFerieUpdateComponent } from './jour-ferie-update.component';
import { JourFerieDeleteDialogComponent } from './jour-ferie-delete-dialog.component';
import { jourFerieRoute } from './jour-ferie.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(jourFerieRoute)],
  declarations: [JourFerieComponent, JourFerieDetailComponent, JourFerieUpdateComponent, JourFerieDeleteDialogComponent],
  entryComponents: [JourFerieDeleteDialogComponent]
})
export class HpdJourFerieModule {}
