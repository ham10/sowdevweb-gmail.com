import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { ImmoComponent } from './immo.component';
import { ImmoDetailComponent } from './immo-detail.component';
import { ImmoUpdateComponent } from './immo-update.component';
import { ImmoDeleteDialogComponent } from './immo-delete-dialog.component';
import { immoRoute } from './immo.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(immoRoute)],
  declarations: [ImmoComponent, ImmoDetailComponent, ImmoUpdateComponent, ImmoDeleteDialogComponent],
  entryComponents: [ImmoDeleteDialogComponent]
})
export class HpdImmoModule {}
