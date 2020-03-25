import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBoxe } from 'app/shared/model/boxe.model';
import { BoxeService } from './boxe.service';

@Component({
  templateUrl: './boxe-delete-dialog.component.html'
})
export class BoxeDeleteDialogComponent {
  boxe?: IBoxe;

  constructor(protected boxeService: BoxeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.boxeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('boxeListModification');
      this.activeModal.close();
    });
  }
}
