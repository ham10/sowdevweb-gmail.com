import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInventaire } from 'app/shared/model/inventaire.model';
import { InventaireService } from './inventaire.service';

@Component({
  templateUrl: './inventaire-delete-dialog.component.html'
})
export class InventaireDeleteDialogComponent {
  inventaire?: IInventaire;

  constructor(
    protected inventaireService: InventaireService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.inventaireService.delete(id).subscribe(() => {
      this.eventManager.broadcast('inventaireListModification');
      this.activeModal.close();
    });
  }
}
