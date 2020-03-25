import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypePlateau } from 'app/shared/model/type-plateau.model';
import { TypePlateauService } from './type-plateau.service';

@Component({
  templateUrl: './type-plateau-delete-dialog.component.html'
})
export class TypePlateauDeleteDialogComponent {
  typePlateau?: ITypePlateau;

  constructor(
    protected typePlateauService: TypePlateauService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typePlateauService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typePlateauListModification');
      this.activeModal.close();
    });
  }
}
