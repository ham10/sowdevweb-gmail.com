import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INatureOp } from 'app/shared/model/nature-op.model';
import { NatureOpService } from './nature-op.service';

@Component({
  templateUrl: './nature-op-delete-dialog.component.html'
})
export class NatureOpDeleteDialogComponent {
  natureOp?: INatureOp;

  constructor(protected natureOpService: NatureOpService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.natureOpService.delete(id).subscribe(() => {
      this.eventManager.broadcast('natureOpListModification');
      this.activeModal.close();
    });
  }
}
