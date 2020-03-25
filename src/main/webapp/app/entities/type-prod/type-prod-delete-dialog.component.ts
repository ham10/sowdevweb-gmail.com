import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeProd } from 'app/shared/model/type-prod.model';
import { TypeProdService } from './type-prod.service';

@Component({
  templateUrl: './type-prod-delete-dialog.component.html'
})
export class TypeProdDeleteDialogComponent {
  typeProd?: ITypeProd;

  constructor(protected typeProdService: TypeProdService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeProdService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeProdListModification');
      this.activeModal.close();
    });
  }
}
