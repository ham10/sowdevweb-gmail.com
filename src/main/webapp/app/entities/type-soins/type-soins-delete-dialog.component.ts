import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeSoins } from 'app/shared/model/type-soins.model';
import { TypeSoinsService } from './type-soins.service';

@Component({
  templateUrl: './type-soins-delete-dialog.component.html'
})
export class TypeSoinsDeleteDialogComponent {
  typeSoins?: ITypeSoins;

  constructor(protected typeSoinsService: TypeSoinsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeSoinsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeSoinsListModification');
      this.activeModal.close();
    });
  }
}
