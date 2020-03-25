import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeCond } from 'app/shared/model/type-cond.model';
import { TypeCondService } from './type-cond.service';

@Component({
  templateUrl: './type-cond-delete-dialog.component.html'
})
export class TypeCondDeleteDialogComponent {
  typeCond?: ITypeCond;

  constructor(protected typeCondService: TypeCondService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeCondService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeCondListModification');
      this.activeModal.close();
    });
  }
}
