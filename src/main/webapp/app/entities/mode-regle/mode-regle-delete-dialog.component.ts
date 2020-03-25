import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IModeRegle } from 'app/shared/model/mode-regle.model';
import { ModeRegleService } from './mode-regle.service';

@Component({
  templateUrl: './mode-regle-delete-dialog.component.html'
})
export class ModeRegleDeleteDialogComponent {
  modeRegle?: IModeRegle;

  constructor(protected modeRegleService: ModeRegleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.modeRegleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('modeRegleListModification');
      this.activeModal.close();
    });
  }
}
