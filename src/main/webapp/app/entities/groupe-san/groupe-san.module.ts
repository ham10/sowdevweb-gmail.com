import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { GroupeSanComponent } from './groupe-san.component';
import { GroupeSanDetailComponent } from './groupe-san-detail.component';
import { GroupeSanUpdateComponent } from './groupe-san-update.component';
import { GroupeSanDeleteDialogComponent } from './groupe-san-delete-dialog.component';
import { groupeSanRoute } from './groupe-san.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(groupeSanRoute)],
  declarations: [GroupeSanComponent, GroupeSanDetailComponent, GroupeSanUpdateComponent, GroupeSanDeleteDialogComponent],
  entryComponents: [GroupeSanDeleteDialogComponent]
})
export class HpdGroupeSanModule {}
