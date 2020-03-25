import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISitMat } from 'app/shared/model/sit-mat.model';
import { SitMatService } from './sit-mat.service';

@Component({
  templateUrl: './sit-mat-delete-dialog.component.html'
})
export class SitMatDeleteDialogComponent {
  sitMat?: ISitMat;

  constructor(protected sitMatService: SitMatService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sitMatService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sitMatListModification');
      this.activeModal.close();
    });
  }
}
