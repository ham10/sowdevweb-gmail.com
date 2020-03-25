import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { MonnaieComponent } from './monnaie.component';
import { MonnaieDetailComponent } from './monnaie-detail.component';
import { MonnaieUpdateComponent } from './monnaie-update.component';
import { MonnaieDeleteDialogComponent } from './monnaie-delete-dialog.component';
import { monnaieRoute } from './monnaie.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(monnaieRoute)],
  declarations: [MonnaieComponent, MonnaieDetailComponent, MonnaieUpdateComponent, MonnaieDeleteDialogComponent],
  entryComponents: [MonnaieDeleteDialogComponent]
})
export class HpdMonnaieModule {}
