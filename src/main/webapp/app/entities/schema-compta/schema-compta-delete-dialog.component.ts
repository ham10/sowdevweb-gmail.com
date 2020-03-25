import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISchemaCompta } from 'app/shared/model/schema-compta.model';
import { SchemaComptaService } from './schema-compta.service';

@Component({
  templateUrl: './schema-compta-delete-dialog.component.html'
})
export class SchemaComptaDeleteDialogComponent {
  schemaCompta?: ISchemaCompta;

  constructor(
    protected schemaComptaService: SchemaComptaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.schemaComptaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('schemaComptaListModification');
      this.activeModal.close();
    });
  }
}
