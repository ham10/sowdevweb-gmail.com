import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEcheancier } from 'app/shared/model/echeancier.model';
import { EcheancierService } from './echeancier.service';

@Component({
  templateUrl: './echeancier-delete-dialog.component.html'
})
export class EcheancierDeleteDialogComponent {
  echeancier?: IEcheancier;

  constructor(
    protected echeancierService: EcheancierService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.echeancierService.delete(id).subscribe(() => {
      this.eventManager.broadcast('echeancierListModification');
      this.activeModal.close();
    });
  }
}
