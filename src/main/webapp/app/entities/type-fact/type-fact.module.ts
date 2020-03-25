import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypeFactComponent } from './type-fact.component';
import { TypeFactDetailComponent } from './type-fact-detail.component';
import { TypeFactUpdateComponent } from './type-fact-update.component';
import { TypeFactDeleteDialogComponent } from './type-fact-delete-dialog.component';
import { typeFactRoute } from './type-fact.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typeFactRoute)],
  declarations: [TypeFactComponent, TypeFactDetailComponent, TypeFactUpdateComponent, TypeFactDeleteDialogComponent],
  entryComponents: [TypeFactDeleteDialogComponent]
})
export class HpdTypeFactModule {}
