import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRayon } from 'app/shared/model/rayon.model';
import { RayonService } from './rayon.service';

@Component({
  templateUrl: './rayon-delete-dialog.component.html'
})
export class RayonDeleteDialogComponent {
  rayon?: IRayon;

  constructor(protected rayonService: RayonService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rayonService.delete(id).subscribe(() => {
      this.eventManager.broadcast('rayonListModification');
      this.activeModal.close();
    });
  }
}
