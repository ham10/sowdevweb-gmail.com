import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVaccin } from 'app/shared/model/vaccin.model';
import { VaccinService } from './vaccin.service';

@Component({
  templateUrl: './vaccin-delete-dialog.component.html'
})
export class VaccinDeleteDialogComponent {
  vaccin?: IVaccin;

  constructor(protected vaccinService: VaccinService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vaccinService.delete(id).subscribe(() => {
      this.eventManager.broadcast('vaccinListModification');
      this.activeModal.close();
    });
  }
}
