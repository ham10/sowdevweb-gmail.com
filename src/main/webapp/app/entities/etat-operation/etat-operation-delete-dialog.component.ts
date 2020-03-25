import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEtatOperation } from 'app/shared/model/etat-operation.model';
import { EtatOperationService } from './etat-operation.service';

@Component({
  templateUrl: './etat-operation-delete-dialog.component.html'
})
export class EtatOperationDeleteDialogComponent {
  etatOperation?: IEtatOperation;

  constructor(
    protected etatOperationService: EtatOperationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.etatOperationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('etatOperationListModification');
      this.activeModal.close();
    });
  }
}
