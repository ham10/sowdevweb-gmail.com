import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { ParamCodeComponent } from './param-code.component';
import { ParamCodeDetailComponent } from './param-code-detail.component';
import { ParamCodeUpdateComponent } from './param-code-update.component';
import { ParamCodeDeleteDialogComponent } from './param-code-delete-dialog.component';
import { paramCodeRoute } from './param-code.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(paramCodeRoute)],
  declarations: [ParamCodeComponent, ParamCodeDetailComponent, ParamCodeUpdateComponent, ParamCodeDeleteDialogComponent],
  entryComponents: [ParamCodeDeleteDialogComponent]
})
export class HpdParamCodeModule {}
