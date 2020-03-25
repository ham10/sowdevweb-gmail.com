import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypeServicesComponent } from './type-services.component';
import { TypeServicesDetailComponent } from './type-services-detail.component';
import { TypeServicesUpdateComponent } from './type-services-update.component';
import { TypeServicesDeleteDialogComponent } from './type-services-delete-dialog.component';
import { typeServicesRoute } from './type-services.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typeServicesRoute)],
  declarations: [TypeServicesComponent, TypeServicesDetailComponent, TypeServicesUpdateComponent, TypeServicesDeleteDialogComponent],
  entryComponents: [TypeServicesDeleteDialogComponent]
})
export class HpdTypeServicesModule {}
