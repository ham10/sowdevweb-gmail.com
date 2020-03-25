import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeCaisse } from 'app/shared/model/type-caisse.model';
import { TypeCaisseService } from './type-caisse.service';

@Component({
  templateUrl: './type-caisse-delete-dialog.component.html'
})
export class TypeCaisseDeleteDialogComponent {
  typeCaisse?: ITypeCaisse;

  constructor(
    protected typeCaisseService: TypeCaisseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeCaisseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeCaisseListModification');
      this.activeModal.close();
    });
  }
}
