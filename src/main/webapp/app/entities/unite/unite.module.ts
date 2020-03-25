import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { UniteComponent } from './unite.component';
import { UniteDetailComponent } from './unite-detail.component';
import { UniteUpdateComponent } from './unite-update.component';
import { UniteDeleteDialogComponent } from './unite-delete-dialog.component';
import { uniteRoute } from './unite.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(uniteRoute)],
  declarations: [UniteComponent, UniteDetailComponent, UniteUpdateComponent, UniteDeleteDialogComponent],
  entryComponents: [UniteDeleteDialogComponent]
})
export class HpdUniteModule {}
