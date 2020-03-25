import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { FonctionnaliteComponent } from './fonctionnalite.component';
import { FonctionnaliteDetailComponent } from './fonctionnalite-detail.component';
import { FonctionnaliteUpdateComponent } from './fonctionnalite-update.component';
import { FonctionnaliteDeleteDialogComponent } from './fonctionnalite-delete-dialog.component';
import { fonctionnaliteRoute } from './fonctionnalite.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(fonctionnaliteRoute)],
  declarations: [
    FonctionnaliteComponent,
    FonctionnaliteDetailComponent,
    FonctionnaliteUpdateComponent,
    FonctionnaliteDeleteDialogComponent
  ],
  entryComponents: [FonctionnaliteDeleteDialogComponent]
})
export class HpdFonctionnaliteModule {}
