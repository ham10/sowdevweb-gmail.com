import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlat } from 'app/shared/model/plat.model';
import { PlatService } from './plat.service';

@Component({
  templateUrl: './plat-delete-dialog.component.html'
})
export class PlatDeleteDialogComponent {
  plat?: IPlat;

  constructor(protected platService: PlatService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.platService.delete(id).subscribe(() => {
      this.eventManager.broadcast('platListModification');
      this.activeModal.close();
    });
  }
}
