import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypeReportComponent } from './type-report.component';
import { TypeReportDetailComponent } from './type-report-detail.component';
import { TypeReportUpdateComponent } from './type-report-update.component';
import { TypeReportDeleteDialogComponent } from './type-report-delete-dialog.component';
import { typeReportRoute } from './type-report.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typeReportRoute)],
  declarations: [TypeReportComponent, TypeReportDetailComponent, TypeReportUpdateComponent, TypeReportDeleteDialogComponent],
  entryComponents: [TypeReportDeleteDialogComponent]
})
export class HpdTypeReportModule {}
