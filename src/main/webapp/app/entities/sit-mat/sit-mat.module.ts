import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { SitMatComponent } from './sit-mat.component';
import { SitMatDetailComponent } from './sit-mat-detail.component';
import { SitMatUpdateComponent } from './sit-mat-update.component';
import { SitMatDeleteDialogComponent } from './sit-mat-delete-dialog.component';
import { sitMatRoute } from './sit-mat.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(sitMatRoute)],
  declarations: [SitMatComponent, SitMatDetailComponent, SitMatUpdateComponent, SitMatDeleteDialogComponent],
  entryComponents: [SitMatDeleteDialogComponent]
})
export class HpdSitMatModule {}
