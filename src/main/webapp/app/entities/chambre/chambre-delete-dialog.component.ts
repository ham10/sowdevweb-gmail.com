import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChambre } from 'app/shared/model/chambre.model';
import { ChambreService } from './chambre.service';

@Component({
  templateUrl: './chambre-delete-dialog.component.html'
})
export class ChambreDeleteDialogComponent {
  chambre?: IChambre;

  constructor(protected chambreService: ChambreService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.chambreService.delete(id).subscribe(() => {
      this.eventManager.broadcast('chambreListModification');
      this.activeModal.close();
    });
  }
}
