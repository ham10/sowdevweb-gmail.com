import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { RDVComponent } from './rdv.component';
import { RDVDetailComponent } from './rdv-detail.component';
import { RDVUpdateComponent } from './rdv-update.component';
import { RDVDeleteDialogComponent } from './rdv-delete-dialog.component';
import { rDVRoute } from './rdv.route';
import { RdvHomeComponent } from './rdv-home/rdv-home.component';
import {FlatpickrModule} from "angularx-flatpickr";
import {CalendarModule, DateAdapter} from "angular-calendar";
import {adapterFactory} from "angular-calendar/date-adapters/date-fns";
import {NgbModalModule} from "@ng-bootstrap/ng-bootstrap";

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(rDVRoute),
    NgbModalModule,
    FlatpickrModule.forRoot(),
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory
    })

  ],
  declarations: [RDVComponent, RDVDetailComponent, RDVUpdateComponent, RDVDeleteDialogComponent, RdvHomeComponent],
  entryComponents: [RDVDeleteDialogComponent]
})
export class HpdRDVModule {}
