import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { OrdonnanceComponent } from './ordonnance.component';
import { OrdonnanceDetailComponent } from './ordonnance-detail.component';
import { OrdonnanceUpdateComponent } from './ordonnance-update.component';
import { OrdonnanceDeleteDialogComponent } from './ordonnance-delete-dialog.component';
import { ordonnanceRoute } from './ordonnance.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(ordonnanceRoute)],
  declarations: [OrdonnanceComponent, OrdonnanceDetailComponent, OrdonnanceUpdateComponent, OrdonnanceDeleteDialogComponent],
  entryComponents: [OrdonnanceDeleteDialogComponent]
})
export class HpdOrdonnanceModule {}
