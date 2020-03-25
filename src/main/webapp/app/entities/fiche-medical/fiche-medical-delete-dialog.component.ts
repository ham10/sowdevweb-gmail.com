import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFicheMedical } from 'app/shared/model/fiche-medical.model';
import { FicheMedicalService } from './fiche-medical.service';

@Component({
  templateUrl: './fiche-medical-delete-dialog.component.html'
})
export class FicheMedicalDeleteDialogComponent {
  ficheMedical?: IFicheMedical;

  constructor(
    protected ficheMedicalService: FicheMedicalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ficheMedicalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ficheMedicalListModification');
      this.activeModal.close();
    });
  }
}
