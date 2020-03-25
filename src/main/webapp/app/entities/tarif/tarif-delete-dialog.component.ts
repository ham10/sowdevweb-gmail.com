import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITarif } from 'app/shared/model/tarif.model';
import { TarifService } from './tarif.service';

@Component({
  templateUrl: './tarif-delete-dialog.component.html'
})
export class TarifDeleteDialogComponent {
  tarif?: ITarif;

  constructor(protected tarifService: TarifService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tarifService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tarifListModification');
      this.activeModal.close();
    });
  }
}
