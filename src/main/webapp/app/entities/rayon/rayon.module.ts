import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { RayonComponent } from './rayon.component';
import { RayonDetailComponent } from './rayon-detail.component';
import { RayonUpdateComponent } from './rayon-update.component';
import { RayonDeleteDialogComponent } from './rayon-delete-dialog.component';
import { rayonRoute } from './rayon.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(rayonRoute)],
  declarations: [RayonComponent, RayonDetailComponent, RayonUpdateComponent, RayonDeleteDialogComponent],
  entryComponents: [RayonDeleteDialogComponent]
})
export class HpdRayonModule {}
