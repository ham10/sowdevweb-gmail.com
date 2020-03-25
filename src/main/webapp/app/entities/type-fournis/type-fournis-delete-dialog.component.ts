import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeFournis } from 'app/shared/model/type-fournis.model';
import { TypeFournisService } from './type-fournis.service';

@Component({
  templateUrl: './type-fournis-delete-dialog.component.html'
})
export class TypeFournisDeleteDialogComponent {
  typeFournis?: ITypeFournis;

  constructor(
    protected typeFournisService: TypeFournisService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeFournisService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeFournisListModification');
      this.activeModal.close();
    });
  }
}
