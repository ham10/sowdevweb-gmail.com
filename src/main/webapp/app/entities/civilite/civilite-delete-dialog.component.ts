import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICivilite } from 'app/shared/model/civilite.model';
import { CiviliteService } from './civilite.service';

@Component({
  templateUrl: './civilite-delete-dialog.component.html'
})
export class CiviliteDeleteDialogComponent {
  civilite?: ICivilite;

  constructor(protected civiliteService: CiviliteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.civiliteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('civiliteListModification');
      this.activeModal.close();
    });
  }
}
