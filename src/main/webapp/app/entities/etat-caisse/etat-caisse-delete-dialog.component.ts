import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEtatCaisse } from 'app/shared/model/etat-caisse.model';
import { EtatCaisseService } from './etat-caisse.service';

@Component({
  templateUrl: './etat-caisse-delete-dialog.component.html'
})
export class EtatCaisseDeleteDialogComponent {
  etatCaisse?: IEtatCaisse;

  constructor(
    protected etatCaisseService: EtatCaisseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.etatCaisseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('etatCaisseListModification');
      this.activeModal.close();
    });
  }
}
