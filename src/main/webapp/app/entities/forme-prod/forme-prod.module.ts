import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { FormeProdComponent } from './forme-prod.component';
import { FormeProdDetailComponent } from './forme-prod-detail.component';
import { FormeProdUpdateComponent } from './forme-prod-update.component';
import { FormeProdDeleteDialogComponent } from './forme-prod-delete-dialog.component';
import { formeProdRoute } from './forme-prod.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(formeProdRoute)],
  declarations: [FormeProdComponent, FormeProdDetailComponent, FormeProdUpdateComponent, FormeProdDeleteDialogComponent],
  entryComponents: [FormeProdDeleteDialogComponent]
})
export class HpdFormeProdModule {}
