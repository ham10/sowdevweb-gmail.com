import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeFacture } from 'app/shared/model/type-facture.model';
import { TypeFactureService } from './type-facture.service';

@Component({
  templateUrl: './type-facture-delete-dialog.component.html'
})
export class TypeFactureDeleteDialogComponent {
  typeFacture?: ITypeFacture;

  constructor(
    protected typeFactureService: TypeFactureService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeFactureService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeFactureListModification');
      this.activeModal.close();
    });
  }
}
