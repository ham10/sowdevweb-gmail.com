import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypeLitComponent } from './type-lit.component';
import { TypeLitDetailComponent } from './type-lit-detail.component';
import { TypeLitUpdateComponent } from './type-lit-update.component';
import { TypeLitDeleteDialogComponent } from './type-lit-delete-dialog.component';
import { typeLitRoute } from './type-lit.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typeLitRoute)],
  declarations: [TypeLitComponent, TypeLitDetailComponent, TypeLitUpdateComponent, TypeLitDeleteDialogComponent],
  entryComponents: [TypeLitDeleteDialogComponent]
})
export class HpdTypeLitModule {}
