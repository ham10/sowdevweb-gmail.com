import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFonctionnalite } from 'app/shared/model/fonctionnalite.model';
import { FonctionnaliteService } from './fonctionnalite.service';

@Component({
  templateUrl: './fonctionnalite-delete-dialog.component.html'
})
export class FonctionnaliteDeleteDialogComponent {
  fonctionnalite?: IFonctionnalite;

  constructor(
    protected fonctionnaliteService: FonctionnaliteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fonctionnaliteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('fonctionnaliteListModification');
      this.activeModal.close();
    });
  }
}
