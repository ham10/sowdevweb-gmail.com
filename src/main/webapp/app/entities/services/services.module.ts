import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { ServicesComponent } from './services.component';
import { ServicesDetailComponent } from './services-detail.component';
import { ServicesUpdateComponent } from './services-update.component';
import { ServicesDeleteDialogComponent } from './services-delete-dialog.component';
import { servicesRoute } from './services.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(servicesRoute)],
  declarations: [ServicesComponent, ServicesDetailComponent, ServicesUpdateComponent, ServicesDeleteDialogComponent],
  entryComponents: [ServicesDeleteDialogComponent]
})
export class HpdServicesModule {}
