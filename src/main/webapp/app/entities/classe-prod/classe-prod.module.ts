import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { ClasseProdComponent } from './classe-prod.component';
import { ClasseProdDetailComponent } from './classe-prod-detail.component';
import { ClasseProdUpdateComponent } from './classe-prod-update.component';
import { ClasseProdDeleteDialogComponent } from './classe-prod-delete-dialog.component';
import { classeProdRoute } from './classe-prod.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(classeProdRoute)],
  declarations: [ClasseProdComponent, ClasseProdDetailComponent, ClasseProdUpdateComponent, ClasseProdDeleteDialogComponent],
  entryComponents: [ClasseProdDeleteDialogComponent]
})
export class HpdClasseProdModule {}
