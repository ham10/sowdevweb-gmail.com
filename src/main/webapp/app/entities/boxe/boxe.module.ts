import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { BoxeComponent } from './boxe.component';
import { BoxeDetailComponent } from './boxe-detail.component';
import { BoxeUpdateComponent } from './boxe-update.component';
import { BoxeDeleteDialogComponent } from './boxe-delete-dialog.component';
import { boxeRoute } from './boxe.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(boxeRoute)],
  declarations: [BoxeComponent, BoxeDetailComponent, BoxeUpdateComponent, BoxeDeleteDialogComponent],
  entryComponents: [BoxeDeleteDialogComponent]
})
export class HpdBoxeModule {}
