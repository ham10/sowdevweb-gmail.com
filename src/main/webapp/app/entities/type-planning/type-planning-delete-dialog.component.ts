import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypePlanning } from 'app/shared/model/type-planning.model';
import { TypePlanningService } from './type-planning.service';

@Component({
  templateUrl: './type-planning-delete-dialog.component.html'
})
export class TypePlanningDeleteDialogComponent {
  typePlanning?: ITypePlanning;

  constructor(
    protected typePlanningService: TypePlanningService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typePlanningService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typePlanningListModification');
      this.activeModal.close();
    });
  }
}
