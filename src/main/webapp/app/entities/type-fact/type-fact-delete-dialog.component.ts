import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeFact } from 'app/shared/model/type-fact.model';
import { TypeFactService } from './type-fact.service';

@Component({
  templateUrl: './type-fact-delete-dialog.component.html'
})
export class TypeFactDeleteDialogComponent {
  typeFact?: ITypeFact;

  constructor(protected typeFactService: TypeFactService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeFactService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeFactListModification');
      this.activeModal.close();
    });
  }
}
