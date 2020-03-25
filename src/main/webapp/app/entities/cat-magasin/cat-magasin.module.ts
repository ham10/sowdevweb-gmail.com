import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { CatMagasinComponent } from './cat-magasin.component';
import { CatMagasinDetailComponent } from './cat-magasin-detail.component';
import { CatMagasinUpdateComponent } from './cat-magasin-update.component';
import { CatMagasinDeleteDialogComponent } from './cat-magasin-delete-dialog.component';
import { catMagasinRoute } from './cat-magasin.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(catMagasinRoute)],
  declarations: [CatMagasinComponent, CatMagasinDetailComponent, CatMagasinUpdateComponent, CatMagasinDeleteDialogComponent],
  entryComponents: [CatMagasinDeleteDialogComponent]
})
export class HpdCatMagasinModule {}
