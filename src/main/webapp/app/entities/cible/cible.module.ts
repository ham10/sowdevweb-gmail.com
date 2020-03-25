import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { CibleComponent } from './cible.component';
import { CibleDetailComponent } from './cible-detail.component';
import { CibleUpdateComponent } from './cible-update.component';
import { CibleDeleteDialogComponent } from './cible-delete-dialog.component';
import { cibleRoute } from './cible.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(cibleRoute)],
  declarations: [CibleComponent, CibleDetailComponent, CibleUpdateComponent, CibleDeleteDialogComponent],
  entryComponents: [CibleDeleteDialogComponent]
})
export class HpdCibleModule {}
