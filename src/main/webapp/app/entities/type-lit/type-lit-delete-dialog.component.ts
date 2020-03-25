import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeLit } from 'app/shared/model/type-lit.model';
import { TypeLitService } from './type-lit.service';

@Component({
  templateUrl: './type-lit-delete-dialog.component.html'
})
export class TypeLitDeleteDialogComponent {
  typeLit?: ITypeLit;

  constructor(protected typeLitService: TypeLitService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeLitService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeLitListModification');
      this.activeModal.close();
    });
  }
}
