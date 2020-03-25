import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDosMedical } from 'app/shared/model/dos-medical.model';
import { DosMedicalService } from './dos-medical.service';

@Component({
  templateUrl: './dos-medical-delete-dialog.component.html'
})
export class DosMedicalDeleteDialogComponent {
  dosMedical?: IDosMedical;

  constructor(
    protected dosMedicalService: DosMedicalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dosMedicalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dosMedicalListModification');
      this.activeModal.close();
    });
  }
}
