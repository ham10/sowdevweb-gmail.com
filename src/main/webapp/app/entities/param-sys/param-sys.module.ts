import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { ParamSysComponent } from './param-sys.component';
import { ParamSysDetailComponent } from './param-sys-detail.component';
import { ParamSysUpdateComponent } from './param-sys-update.component';
import { ParamSysDeleteDialogComponent } from './param-sys-delete-dialog.component';
import { paramSysRoute } from './param-sys.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(paramSysRoute)],
  declarations: [ParamSysComponent, ParamSysDetailComponent, ParamSysUpdateComponent, ParamSysDeleteDialogComponent],
  entryComponents: [ParamSysDeleteDialogComponent]
})
export class HpdParamSysModule {}
