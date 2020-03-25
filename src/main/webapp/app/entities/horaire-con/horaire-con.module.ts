import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { HoraireConComponent } from './horaire-con.component';
import { HoraireConDetailComponent } from './horaire-con-detail.component';
import { HoraireConUpdateComponent } from './horaire-con-update.component';
import { HoraireConDeleteDialogComponent } from './horaire-con-delete-dialog.component';
import { horaireConRoute } from './horaire-con.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(horaireConRoute)],
  declarations: [HoraireConComponent, HoraireConDetailComponent, HoraireConUpdateComponent, HoraireConDeleteDialogComponent],
  entryComponents: [HoraireConDeleteDialogComponent]
})
export class HpdHoraireConModule {}
