import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IImmo } from 'app/shared/model/immo.model';
import { ImmoService } from './immo.service';

@Component({
  templateUrl: './immo-delete-dialog.component.html'
})
export class ImmoDeleteDialogComponent {
  immo?: IImmo;

  constructor(protected immoService: ImmoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.immoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('immoListModification');
      this.activeModal.close();
    });
  }
}
