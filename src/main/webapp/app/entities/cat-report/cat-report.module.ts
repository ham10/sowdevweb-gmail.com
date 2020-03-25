import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { CatReportComponent } from './cat-report.component';
import { CatReportDetailComponent } from './cat-report-detail.component';
import { CatReportUpdateComponent } from './cat-report-update.component';
import { CatReportDeleteDialogComponent } from './cat-report-delete-dialog.component';
import { catReportRoute } from './cat-report.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(catReportRoute)],
  declarations: [CatReportComponent, CatReportDetailComponent, CatReportUpdateComponent, CatReportDeleteDialogComponent],
  entryComponents: [CatReportDeleteDialogComponent]
})
export class HpdCatReportModule {}
