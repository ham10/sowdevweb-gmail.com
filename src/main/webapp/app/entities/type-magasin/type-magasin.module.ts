import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypeMagasinComponent } from './type-magasin.component';
import { TypeMagasinDetailComponent } from './type-magasin-detail.component';
import { TypeMagasinUpdateComponent } from './type-magasin-update.component';
import { TypeMagasinDeleteDialogComponent } from './type-magasin-delete-dialog.component';
import { typeMagasinRoute } from './type-magasin.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typeMagasinRoute)],
  declarations: [TypeMagasinComponent, TypeMagasinDetailComponent, TypeMagasinUpdateComponent, TypeMagasinDeleteDialogComponent],
  entryComponents: [TypeMagasinDeleteDialogComponent]
})
export class HpdTypeMagasinModule {}
