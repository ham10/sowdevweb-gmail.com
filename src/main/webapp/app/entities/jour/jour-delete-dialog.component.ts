import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJour } from 'app/shared/model/jour.model';
import { JourService } from './jour.service';

@Component({
  templateUrl: './jour-delete-dialog.component.html'
})
export class JourDeleteDialogComponent {
  jour?: IJour;

  constructor(protected jourService: JourService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jourService.delete(id).subscribe(() => {
      this.eventManager.broadcast('jourListModification');
      this.activeModal.close();
    });
  }
}
