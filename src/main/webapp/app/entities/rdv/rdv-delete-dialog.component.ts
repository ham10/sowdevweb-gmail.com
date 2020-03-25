import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRDV } from 'app/shared/model/rdv.model';
import { RDVService } from './rdv.service';

@Component({
  templateUrl: './rdv-delete-dialog.component.html'
})
export class RDVDeleteDialogComponent {
  rDV?: IRDV;

  constructor(protected rDVService: RDVService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rDVService.delete(id).subscribe(() => {
      this.eventManager.broadcast('rDVListModification');
      this.activeModal.close();
    });
  }
}
