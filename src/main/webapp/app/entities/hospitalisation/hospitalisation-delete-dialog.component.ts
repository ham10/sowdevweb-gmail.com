import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHospitalisation } from 'app/shared/model/hospitalisation.model';
import { HospitalisationService } from './hospitalisation.service';

@Component({
  templateUrl: './hospitalisation-delete-dialog.component.html'
})
export class HospitalisationDeleteDialogComponent {
  hospitalisation?: IHospitalisation;

  constructor(
    protected hospitalisationService: HospitalisationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hospitalisationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('hospitalisationListModification');
      this.activeModal.close();
    });
  }
}
