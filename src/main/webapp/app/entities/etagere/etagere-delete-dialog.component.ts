import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEtagere } from 'app/shared/model/etagere.model';
import { EtagereService } from './etagere.service';

@Component({
  templateUrl: './etagere-delete-dialog.component.html'
})
export class EtagereDeleteDialogComponent {
  etagere?: IEtagere;

  constructor(protected etagereService: EtagereService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.etagereService.delete(id).subscribe(() => {
      this.eventManager.broadcast('etagereListModification');
      this.activeModal.close();
    });
  }
}
