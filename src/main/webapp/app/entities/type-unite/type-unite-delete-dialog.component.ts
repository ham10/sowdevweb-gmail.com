import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeUnite } from 'app/shared/model/type-unite.model';
import { TypeUniteService } from './type-unite.service';

@Component({
  templateUrl: './type-unite-delete-dialog.component.html'
})
export class TypeUniteDeleteDialogComponent {
  typeUnite?: ITypeUnite;

  constructor(protected typeUniteService: TypeUniteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeUniteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeUniteListModification');
      this.activeModal.close();
    });
  }
}
