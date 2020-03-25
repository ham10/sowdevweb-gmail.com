import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJourFerie } from 'app/shared/model/jour-ferie.model';
import { JourFerieService } from './jour-ferie.service';

@Component({
  templateUrl: './jour-ferie-delete-dialog.component.html'
})
export class JourFerieDeleteDialogComponent {
  jourFerie?: IJourFerie;

  constructor(protected jourFerieService: JourFerieService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jourFerieService.delete(id).subscribe(() => {
      this.eventManager.broadcast('jourFerieListModification');
      this.activeModal.close();
    });
  }
}
