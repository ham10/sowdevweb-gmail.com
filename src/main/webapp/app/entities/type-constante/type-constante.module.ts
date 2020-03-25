import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypeConstanteComponent } from './type-constante.component';
import { TypeConstanteDetailComponent } from './type-constante-detail.component';
import { TypeConstanteUpdateComponent } from './type-constante-update.component';
import { TypeConstanteDeleteDialogComponent } from './type-constante-delete-dialog.component';
import { typeConstanteRoute } from './type-constante.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typeConstanteRoute)],
  declarations: [TypeConstanteComponent, TypeConstanteDetailComponent, TypeConstanteUpdateComponent, TypeConstanteDeleteDialogComponent],
  entryComponents: [TypeConstanteDeleteDialogComponent]
})
export class HpdTypeConstanteModule {}
