import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEtatPlanning } from 'app/shared/model/etat-planning.model';
import { EtatPlanningService } from './etat-planning.service';

@Component({
  templateUrl: './etat-planning-delete-dialog.component.html'
})
export class EtatPlanningDeleteDialogComponent {
  etatPlanning?: IEtatPlanning;

  constructor(
    protected etatPlanningService: EtatPlanningService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.etatPlanningService.delete(id).subscribe(() => {
      this.eventManager.broadcast('etatPlanningListModification');
      this.activeModal.close();
    });
  }
}
