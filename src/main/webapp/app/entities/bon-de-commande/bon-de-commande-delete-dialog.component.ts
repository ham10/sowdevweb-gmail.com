import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBonDeCommande } from 'app/shared/model/bon-de-commande.model';
import { BonDeCommandeService } from './bon-de-commande.service';

@Component({
  templateUrl: './bon-de-commande-delete-dialog.component.html'
})
export class BonDeCommandeDeleteDialogComponent {
  bonDeCommande?: IBonDeCommande;

  constructor(
    protected bonDeCommandeService: BonDeCommandeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bonDeCommandeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bonDeCommandeListModification');
      this.activeModal.close();
    });
  }
}
