import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEtatFacture } from 'app/shared/model/etat-facture.model';
import { EtatFactureService } from './etat-facture.service';

@Component({
  templateUrl: './etat-facture-delete-dialog.component.html'
})
export class EtatFactureDeleteDialogComponent {
  etatFacture?: IEtatFacture;

  constructor(
    protected etatFactureService: EtatFactureService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.etatFactureService.delete(id).subscribe(() => {
      this.eventManager.broadcast('etatFactureListModification');
      this.activeModal.close();
    });
  }
}
