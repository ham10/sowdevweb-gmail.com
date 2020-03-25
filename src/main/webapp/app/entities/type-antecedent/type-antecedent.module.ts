import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypeAntecedentComponent } from './type-antecedent.component';
import { TypeAntecedentDetailComponent } from './type-antecedent-detail.component';
import { TypeAntecedentUpdateComponent } from './type-antecedent-update.component';
import { TypeAntecedentDeleteDialogComponent } from './type-antecedent-delete-dialog.component';
import { typeAntecedentRoute } from './type-antecedent.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typeAntecedentRoute)],
  declarations: [
    TypeAntecedentComponent,
    TypeAntecedentDetailComponent,
    TypeAntecedentUpdateComponent,
    TypeAntecedentDeleteDialogComponent
  ],
  entryComponents: [TypeAntecedentDeleteDialogComponent]
})
export class HpdTypeAntecedentModule {}
