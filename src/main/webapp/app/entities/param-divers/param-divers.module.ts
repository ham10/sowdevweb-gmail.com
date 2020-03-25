import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { ParamDiversComponent } from './param-divers.component';
import { ParamDiversDetailComponent } from './param-divers-detail.component';
import { ParamDiversUpdateComponent } from './param-divers-update.component';
import { ParamDiversDeleteDialogComponent } from './param-divers-delete-dialog.component';
import { paramDiversRoute } from './param-divers.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(paramDiversRoute)],
  declarations: [ParamDiversComponent, ParamDiversDetailComponent, ParamDiversUpdateComponent, ParamDiversDeleteDialogComponent],
  entryComponents: [ParamDiversDeleteDialogComponent]
})
export class HpdParamDiversModule {}
