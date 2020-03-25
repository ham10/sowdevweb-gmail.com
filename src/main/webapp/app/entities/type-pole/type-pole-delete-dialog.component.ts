import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypePole } from 'app/shared/model/type-pole.model';
import { TypePoleService } from './type-pole.service';

@Component({
  templateUrl: './type-pole-delete-dialog.component.html'
})
export class TypePoleDeleteDialogComponent {
  typePole?: ITypePole;

  constructor(protected typePoleService: TypePoleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typePoleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typePoleListModification');
      this.activeModal.close();
    });
  }
}
