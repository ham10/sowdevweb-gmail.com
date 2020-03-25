import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEtatImmo } from 'app/shared/model/etat-immo.model';
import { EtatImmoService } from './etat-immo.service';

@Component({
  templateUrl: './etat-immo-delete-dialog.component.html'
})
export class EtatImmoDeleteDialogComponent {
  etatImmo?: IEtatImmo;

  constructor(protected etatImmoService: EtatImmoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.etatImmoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('etatImmoListModification');
      this.activeModal.close();
    });
  }
}
