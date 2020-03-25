import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TabAmortisComponent } from './tab-amortis.component';
import { TabAmortisDetailComponent } from './tab-amortis-detail.component';
import { TabAmortisUpdateComponent } from './tab-amortis-update.component';
import { TabAmortisDeleteDialogComponent } from './tab-amortis-delete-dialog.component';
import { tabAmortisRoute } from './tab-amortis.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(tabAmortisRoute)],
  declarations: [TabAmortisComponent, TabAmortisDetailComponent, TabAmortisUpdateComponent, TabAmortisDeleteDialogComponent],
  entryComponents: [TabAmortisDeleteDialogComponent]
})
export class HpdTabAmortisModule {}
