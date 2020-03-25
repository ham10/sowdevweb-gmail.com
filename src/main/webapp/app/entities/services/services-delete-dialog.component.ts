import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServices } from 'app/shared/model/services.model';
import { ServicesService } from './services.service';

@Component({
  templateUrl: './services-delete-dialog.component.html'
})
export class ServicesDeleteDialogComponent {
  services?: IServices;

  constructor(protected servicesService: ServicesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.servicesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('servicesListModification');
      this.activeModal.close();
    });
  }
}
