import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICible } from 'app/shared/model/cible.model';
import { CibleService } from './cible.service';

@Component({
  templateUrl: './cible-delete-dialog.component.html'
})
export class CibleDeleteDialogComponent {
  cible?: ICible;

  constructor(protected cibleService: CibleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cibleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cibleListModification');
      this.activeModal.close();
    });
  }
}
