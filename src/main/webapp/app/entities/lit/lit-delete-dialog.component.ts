import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILit } from 'app/shared/model/lit.model';
import { LitService } from './lit.service';

@Component({
  templateUrl: './lit-delete-dialog.component.html'
})
export class LitDeleteDialogComponent {
  lit?: ILit;

  constructor(protected litService: LitService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.litService.delete(id).subscribe(() => {
      this.eventManager.broadcast('litListModification');
      this.activeModal.close();
    });
  }
}
