import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICatMagasin } from 'app/shared/model/cat-magasin.model';
import { CatMagasinService } from './cat-magasin.service';

@Component({
  templateUrl: './cat-magasin-delete-dialog.component.html'
})
export class CatMagasinDeleteDialogComponent {
  catMagasin?: ICatMagasin;

  constructor(
    protected catMagasinService: CatMagasinService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.catMagasinService.delete(id).subscribe(() => {
      this.eventManager.broadcast('catMagasinListModification');
      this.activeModal.close();
    });
  }
}
