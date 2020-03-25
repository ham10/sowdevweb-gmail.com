import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEcriture } from 'app/shared/model/ecriture.model';
import { EcritureService } from './ecriture.service';

@Component({
  templateUrl: './ecriture-delete-dialog.component.html'
})
export class EcritureDeleteDialogComponent {
  ecriture?: IEcriture;

  constructor(protected ecritureService: EcritureService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ecritureService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ecritureListModification');
      this.activeModal.close();
    });
  }
}
