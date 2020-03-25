import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { DeptServicesComponent } from './dept-services.component';
import { DeptServicesDetailComponent } from './dept-services-detail.component';
import { DeptServicesUpdateComponent } from './dept-services-update.component';
import { DeptServicesDeleteDialogComponent } from './dept-services-delete-dialog.component';
import { deptServicesRoute } from './dept-services.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(deptServicesRoute)],
  declarations: [DeptServicesComponent, DeptServicesDetailComponent, DeptServicesUpdateComponent, DeptServicesDeleteDialogComponent],
  entryComponents: [DeptServicesDeleteDialogComponent]
})
export class HpdDeptServicesModule {}
