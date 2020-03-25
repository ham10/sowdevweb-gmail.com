import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProdFournis } from 'app/shared/model/prod-fournis.model';
import { ProdFournisService } from './prod-fournis.service';

@Component({
  templateUrl: './prod-fournis-delete-dialog.component.html'
})
export class ProdFournisDeleteDialogComponent {
  prodFournis?: IProdFournis;

  constructor(
    protected prodFournisService: ProdFournisService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.prodFournisService.delete(id).subscribe(() => {
      this.eventManager.broadcast('prodFournisListModification');
      this.activeModal.close();
    });
  }
}
