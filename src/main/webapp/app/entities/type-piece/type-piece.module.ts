import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypePieceComponent } from './type-piece.component';
import { TypePieceDetailComponent } from './type-piece-detail.component';
import { TypePieceUpdateComponent } from './type-piece-update.component';
import { TypePieceDeleteDialogComponent } from './type-piece-delete-dialog.component';
import { typePieceRoute } from './type-piece.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typePieceRoute)],
  declarations: [TypePieceComponent, TypePieceDetailComponent, TypePieceUpdateComponent, TypePieceDeleteDialogComponent],
  entryComponents: [TypePieceDeleteDialogComponent]
})
export class HpdTypePieceModule {}
