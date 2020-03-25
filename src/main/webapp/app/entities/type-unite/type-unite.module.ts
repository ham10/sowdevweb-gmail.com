import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypeUniteComponent } from './type-unite.component';
import { TypeUniteDetailComponent } from './type-unite-detail.component';
import { TypeUniteUpdateComponent } from './type-unite-update.component';
import { TypeUniteDeleteDialogComponent } from './type-unite-delete-dialog.component';
import { typeUniteRoute } from './type-unite.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typeUniteRoute)],
  declarations: [TypeUniteComponent, TypeUniteDetailComponent, TypeUniteUpdateComponent, TypeUniteDeleteDialogComponent],
  entryComponents: [TypeUniteDeleteDialogComponent]
})
export class HpdTypeUniteModule {}
