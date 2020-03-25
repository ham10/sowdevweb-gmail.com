import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { CalendrierComponent } from './calendrier.component';
import { CalendrierDetailComponent } from './calendrier-detail.component';
import { CalendrierUpdateComponent } from './calendrier-update.component';
import { CalendrierDeleteDialogComponent } from './calendrier-delete-dialog.component';
import { calendrierRoute } from './calendrier.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(calendrierRoute)],
  declarations: [CalendrierComponent, CalendrierDetailComponent, CalendrierUpdateComponent, CalendrierDeleteDialogComponent],
  entryComponents: [CalendrierDeleteDialogComponent]
})
export class HpdCalendrierModule {}
