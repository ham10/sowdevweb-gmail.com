import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { ProdFournisComponent } from './prod-fournis.component';
import { ProdFournisDetailComponent } from './prod-fournis-detail.component';
import { ProdFournisUpdateComponent } from './prod-fournis-update.component';
import { ProdFournisDeleteDialogComponent } from './prod-fournis-delete-dialog.component';
import { prodFournisRoute } from './prod-fournis.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(prodFournisRoute)],
  declarations: [ProdFournisComponent, ProdFournisDetailComponent, ProdFournisUpdateComponent, ProdFournisDeleteDialogComponent],
  entryComponents: [ProdFournisDeleteDialogComponent]
})
export class HpdProdFournisModule {}
