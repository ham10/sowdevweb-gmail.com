import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypeSortieComponent } from './type-sortie.component';
import { TypeSortieDetailComponent } from './type-sortie-detail.component';
import { TypeSortieUpdateComponent } from './type-sortie-update.component';
import { TypeSortieDeleteDialogComponent } from './type-sortie-delete-dialog.component';
import { typeSortieRoute } from './type-sortie.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typeSortieRoute)],
  declarations: [TypeSortieComponent, TypeSortieDetailComponent, TypeSortieUpdateComponent, TypeSortieDeleteDialogComponent],
  entryComponents: [TypeSortieDeleteDialogComponent]
})
export class HpdTypeSortieModule {}
