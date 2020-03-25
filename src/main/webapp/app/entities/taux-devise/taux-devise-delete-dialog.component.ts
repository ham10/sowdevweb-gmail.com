import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITauxDevise } from 'app/shared/model/taux-devise.model';
import { TauxDeviseService } from './taux-devise.service';

@Component({
  templateUrl: './taux-devise-delete-dialog.component.html'
})
export class TauxDeviseDeleteDialogComponent {
  tauxDevise?: ITauxDevise;

  constructor(
    protected tauxDeviseService: TauxDeviseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tauxDeviseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tauxDeviseListModification');
      this.activeModal.close();
    });
  }
}
