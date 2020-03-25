import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { SchemaComptaComponent } from './schema-compta.component';
import { SchemaComptaDetailComponent } from './schema-compta-detail.component';
import { SchemaComptaUpdateComponent } from './schema-compta-update.component';
import { SchemaComptaDeleteDialogComponent } from './schema-compta-delete-dialog.component';
import { schemaComptaRoute } from './schema-compta.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(schemaComptaRoute)],
  declarations: [SchemaComptaComponent, SchemaComptaDetailComponent, SchemaComptaUpdateComponent, SchemaComptaDeleteDialogComponent],
  entryComponents: [SchemaComptaDeleteDialogComponent]
})
export class HpdSchemaComptaModule {}
