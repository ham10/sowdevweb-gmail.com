import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { MachAutoriseComponent } from './mach-autorise.component';
import { MachAutoriseDetailComponent } from './mach-autorise-detail.component';
import { MachAutoriseUpdateComponent } from './mach-autorise-update.component';
import { MachAutoriseDeleteDialogComponent } from './mach-autorise-delete-dialog.component';
import { machAutoriseRoute } from './mach-autorise.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(machAutoriseRoute)],
  declarations: [MachAutoriseComponent, MachAutoriseDetailComponent, MachAutoriseUpdateComponent, MachAutoriseDeleteDialogComponent],
  entryComponents: [MachAutoriseDeleteDialogComponent]
})
export class HpdMachAutoriseModule {}
