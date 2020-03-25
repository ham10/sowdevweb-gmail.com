import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { EvenementComponent } from './evenement.component';
import { EvenementDetailComponent } from './evenement-detail.component';
import { EvenementUpdateComponent } from './evenement-update.component';
import { EvenementDeleteDialogComponent } from './evenement-delete-dialog.component';
import { evenementRoute } from './evenement.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(evenementRoute)],
  declarations: [EvenementComponent, EvenementDetailComponent, EvenementUpdateComponent, EvenementDeleteDialogComponent],
  entryComponents: [EvenementDeleteDialogComponent]
})
export class HpdEvenementModule {}
