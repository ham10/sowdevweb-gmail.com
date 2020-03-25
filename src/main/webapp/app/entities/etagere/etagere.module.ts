import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { EtagereComponent } from './etagere.component';
import { EtagereDetailComponent } from './etagere-detail.component';
import { EtagereUpdateComponent } from './etagere-update.component';
import { EtagereDeleteDialogComponent } from './etagere-delete-dialog.component';
import { etagereRoute } from './etagere.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(etagereRoute)],
  declarations: [EtagereComponent, EtagereDetailComponent, EtagereUpdateComponent, EtagereDeleteDialogComponent],
  entryComponents: [EtagereDeleteDialogComponent]
})
export class HpdEtagereModule {}
