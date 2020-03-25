import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { PlanningComponent } from './planning.component';
import { PlanningDetailComponent } from './planning-detail.component';
import { PlanningUpdateComponent } from './planning-update.component';
import { PlanningDeleteDialogComponent } from './planning-delete-dialog.component';
import { planningRoute } from './planning.route';

import { PlanningHomeComponent } from './planning-home/planning-home.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { FlatpickrModule } from 'angularx-flatpickr';

@NgModule({
  imports: [
    HpdSharedModule,
    RouterModule.forChild(planningRoute),
    CommonModule,
    FormsModule,
    NgbModalModule,
    FlatpickrModule.forRoot(),
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory
    })
  ],
  declarations: [PlanningComponent, PlanningDetailComponent, PlanningUpdateComponent, PlanningDeleteDialogComponent, PlanningHomeComponent],
  entryComponents: [PlanningDeleteDialogComponent]
})
export class HpdPlanningModule {}
