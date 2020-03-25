import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { SousFamilleComponent } from './sous-famille.component';
import { SousFamilleDetailComponent } from './sous-famille-detail.component';
import { SousFamilleUpdateComponent } from './sous-famille-update.component';
import { SousFamilleDeleteDialogComponent } from './sous-famille-delete-dialog.component';
import { sousFamilleRoute } from './sous-famille.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(sousFamilleRoute)],
  declarations: [SousFamilleComponent, SousFamilleDetailComponent, SousFamilleUpdateComponent, SousFamilleDeleteDialogComponent],
  entryComponents: [SousFamilleDeleteDialogComponent]
})
export class HpdSousFamilleModule {}
