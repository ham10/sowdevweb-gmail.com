import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAyantDroit } from 'app/shared/model/ayant-droit.model';
import { AyantDroitService } from './ayant-droit.service';

@Component({
  templateUrl: './ayant-droit-delete-dialog.component.html'
})
export class AyantDroitDeleteDialogComponent {
  ayantDroit?: IAyantDroit;

  constructor(
    protected ayantDroitService: AyantDroitService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ayantDroitService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ayantDroitListModification');
      this.activeModal.close();
    });
  }
}
