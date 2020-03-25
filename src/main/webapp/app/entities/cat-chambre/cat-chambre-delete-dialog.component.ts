import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICatChambre } from 'app/shared/model/cat-chambre.model';
import { CatChambreService } from './cat-chambre.service';

@Component({
  templateUrl: './cat-chambre-delete-dialog.component.html'
})
export class CatChambreDeleteDialogComponent {
  catChambre?: ICatChambre;

  constructor(
    protected catChambreService: CatChambreService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.catChambreService.delete(id).subscribe(() => {
      this.eventManager.broadcast('catChambreListModification');
      this.activeModal.close();
    });
  }
}
