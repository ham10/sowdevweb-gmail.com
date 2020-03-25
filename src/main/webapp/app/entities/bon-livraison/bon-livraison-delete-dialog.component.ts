import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBonLivraison } from 'app/shared/model/bon-livraison.model';
import { BonLivraisonService } from './bon-livraison.service';

@Component({
  templateUrl: './bon-livraison-delete-dialog.component.html'
})
export class BonLivraisonDeleteDialogComponent {
  bonLivraison?: IBonLivraison;

  constructor(
    protected bonLivraisonService: BonLivraisonService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bonLivraisonService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bonLivraisonListModification');
      this.activeModal.close();
    });
  }
}
