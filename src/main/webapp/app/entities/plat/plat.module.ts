import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { PlatComponent } from './plat.component';
import { PlatDetailComponent } from './plat-detail.component';
import { PlatUpdateComponent } from './plat-update.component';
import { PlatDeleteDialogComponent } from './plat-delete-dialog.component';
import { platRoute } from './plat.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(platRoute)],
  declarations: [PlatComponent, PlatDetailComponent, PlatUpdateComponent, PlatDeleteDialogComponent],
  entryComponents: [PlatDeleteDialogComponent]
})
export class HpdPlatModule {}
