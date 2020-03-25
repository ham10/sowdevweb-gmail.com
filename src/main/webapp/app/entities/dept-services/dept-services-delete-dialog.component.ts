import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeptServices } from 'app/shared/model/dept-services.model';
import { DeptServicesService } from './dept-services.service';

@Component({
  templateUrl: './dept-services-delete-dialog.component.html'
})
export class DeptServicesDeleteDialogComponent {
  deptServices?: IDeptServices;

  constructor(
    protected deptServicesService: DeptServicesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.deptServicesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('deptServicesListModification');
      this.activeModal.close();
    });
  }
}
