import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeTarif } from 'app/shared/model/type-tarif.model';
import { TypeTarifService } from './type-tarif.service';

@Component({
  templateUrl: './type-tarif-delete-dialog.component.html'
})
export class TypeTarifDeleteDialogComponent {
  typeTarif?: ITypeTarif;

  constructor(protected typeTarifService: TypeTarifService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeTarifService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeTarifListModification');
      this.activeModal.close();
    });
  }
}
