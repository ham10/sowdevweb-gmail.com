import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { AntecedentComponent } from './antecedent.component';
import { AntecedentDetailComponent } from './antecedent-detail.component';
import { AntecedentUpdateComponent } from './antecedent-update.component';
import { AntecedentDeleteDialogComponent } from './antecedent-delete-dialog.component';
import { antecedentRoute } from './antecedent.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(antecedentRoute)],
  declarations: [AntecedentComponent, AntecedentDetailComponent, AntecedentUpdateComponent, AntecedentDeleteDialogComponent],
  entryComponents: [AntecedentDeleteDialogComponent]
})
export class HpdAntecedentModule {}
