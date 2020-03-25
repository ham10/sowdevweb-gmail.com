import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypePatient } from 'app/shared/model/type-patient.model';
import { TypePatientService } from './type-patient.service';

@Component({
  templateUrl: './type-patient-delete-dialog.component.html'
})
export class TypePatientDeleteDialogComponent {
  typePatient?: ITypePatient;

  constructor(
    protected typePatientService: TypePatientService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typePatientService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typePatientListModification');
      this.activeModal.close();
    });
  }
}
