import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeChamps } from 'app/shared/model/type-champs.model';
import { TypeChampsService } from './type-champs.service';

@Component({
  templateUrl: './type-champs-delete-dialog.component.html'
})
export class TypeChampsDeleteDialogComponent {
  typeChamps?: ITypeChamps;

  constructor(
    protected typeChampsService: TypeChampsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeChampsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeChampsListModification');
      this.activeModal.close();
    });
  }
}
