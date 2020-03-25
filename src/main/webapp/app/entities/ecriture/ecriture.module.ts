import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { EcritureComponent } from './ecriture.component';
import { EcritureDetailComponent } from './ecriture-detail.component';
import { EcritureUpdateComponent } from './ecriture-update.component';
import { EcritureDeleteDialogComponent } from './ecriture-delete-dialog.component';
import { ecritureRoute } from './ecriture.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(ecritureRoute)],
  declarations: [EcritureComponent, EcritureDetailComponent, EcritureUpdateComponent, EcritureDeleteDialogComponent],
  entryComponents: [EcritureDeleteDialogComponent]
})
export class HpdEcritureModule {}
