import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypeNotifComponent } from './type-notif.component';
import { TypeNotifDetailComponent } from './type-notif-detail.component';
import { TypeNotifUpdateComponent } from './type-notif-update.component';
import { TypeNotifDeleteDialogComponent } from './type-notif-delete-dialog.component';
import { typeNotifRoute } from './type-notif.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typeNotifRoute)],
  declarations: [TypeNotifComponent, TypeNotifDetailComponent, TypeNotifUpdateComponent, TypeNotifDeleteDialogComponent],
  entryComponents: [TypeNotifDeleteDialogComponent]
})
export class HpdTypeNotifModule {}
