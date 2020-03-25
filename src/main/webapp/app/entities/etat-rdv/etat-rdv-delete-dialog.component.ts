import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEtatRdv } from 'app/shared/model/etat-rdv.model';
import { EtatRdvService } from './etat-rdv.service';

@Component({
  templateUrl: './etat-rdv-delete-dialog.component.html'
})
export class EtatRdvDeleteDialogComponent {
  etatRdv?: IEtatRdv;

  constructor(protected etatRdvService: EtatRdvService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.etatRdvService.delete(id).subscribe(() => {
      this.eventManager.broadcast('etatRdvListModification');
      this.activeModal.close();
    });
  }
}
