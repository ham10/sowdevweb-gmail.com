import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeImmo } from 'app/shared/model/type-immo.model';
import { TypeImmoService } from './type-immo.service';

@Component({
  templateUrl: './type-immo-delete-dialog.component.html'
})
export class TypeImmoDeleteDialogComponent {
  typeImmo?: ITypeImmo;

  constructor(protected typeImmoService: TypeImmoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeImmoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeImmoListModification');
      this.activeModal.close();
    });
  }
}
