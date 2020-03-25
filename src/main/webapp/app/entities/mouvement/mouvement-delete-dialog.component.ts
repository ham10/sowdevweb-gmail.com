import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMouvement } from 'app/shared/model/mouvement.model';
import { MouvementService } from './mouvement.service';

@Component({
  templateUrl: './mouvement-delete-dialog.component.html'
})
export class MouvementDeleteDialogComponent {
  mouvement?: IMouvement;

  constructor(protected mouvementService: MouvementService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mouvementService.delete(id).subscribe(() => {
      this.eventManager.broadcast('mouvementListModification');
      this.activeModal.close();
    });
  }
}
