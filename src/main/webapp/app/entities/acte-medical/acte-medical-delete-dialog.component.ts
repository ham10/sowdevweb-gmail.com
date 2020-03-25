import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IActeMedical } from 'app/shared/model/acte-medical.model';
import { ActeMedicalService } from './acte-medical.service';

@Component({
  templateUrl: './acte-medical-delete-dialog.component.html'
})
export class ActeMedicalDeleteDialogComponent {
  acteMedical?: IActeMedical;

  constructor(
    protected acteMedicalService: ActeMedicalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.acteMedicalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('acteMedicalListModification');
      this.activeModal.close();
    });
  }
}
