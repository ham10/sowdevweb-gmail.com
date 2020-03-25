import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPole } from 'app/shared/model/pole.model';
import { PoleService } from './pole.service';

@Component({
  templateUrl: './pole-delete-dialog.component.html'
})
export class PoleDeleteDialogComponent {
  pole?: IPole;

  constructor(protected poleService: PoleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.poleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('poleListModification');
      this.activeModal.close();
    });
  }
}
