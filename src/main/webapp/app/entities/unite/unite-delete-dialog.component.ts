import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUnite } from 'app/shared/model/unite.model';
import { UniteService } from './unite.service';

@Component({
  templateUrl: './unite-delete-dialog.component.html'
})
export class UniteDeleteDialogComponent {
  unite?: IUnite;

  constructor(protected uniteService: UniteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.uniteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('uniteListModification');
      this.activeModal.close();
    });
  }
}
