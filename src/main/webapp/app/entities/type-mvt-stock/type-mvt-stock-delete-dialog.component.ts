import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeMvtStock } from 'app/shared/model/type-mvt-stock.model';
import { TypeMvtStockService } from './type-mvt-stock.service';

@Component({
  templateUrl: './type-mvt-stock-delete-dialog.component.html'
})
export class TypeMvtStockDeleteDialogComponent {
  typeMvtStock?: ITypeMvtStock;

  constructor(
    protected typeMvtStockService: TypeMvtStockService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeMvtStockService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeMvtStockListModification');
      this.activeModal.close();
    });
  }
}
