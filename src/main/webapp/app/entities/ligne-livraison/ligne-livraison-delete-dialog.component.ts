import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILigneLivraison } from 'app/shared/model/ligne-livraison.model';
import { LigneLivraisonService } from './ligne-livraison.service';

@Component({
  templateUrl: './ligne-livraison-delete-dialog.component.html'
})
export class LigneLivraisonDeleteDialogComponent {
  ligneLivraison?: ILigneLivraison;

  constructor(
    protected ligneLivraisonService: LigneLivraisonService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ligneLivraisonService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ligneLivraisonListModification');
      this.activeModal.close();
    });
  }
}
