import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDetailPlanning } from 'app/shared/model/detail-planning.model';
import { DetailPlanningService } from './detail-planning.service';

@Component({
  templateUrl: './detail-planning-delete-dialog.component.html'
})
export class DetailPlanningDeleteDialogComponent {
  detailPlanning?: IDetailPlanning;

  constructor(
    protected detailPlanningService: DetailPlanningService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.detailPlanningService.delete(id).subscribe(() => {
      this.eventManager.broadcast('detailPlanningListModification');
      this.activeModal.close();
    });
  }
}
