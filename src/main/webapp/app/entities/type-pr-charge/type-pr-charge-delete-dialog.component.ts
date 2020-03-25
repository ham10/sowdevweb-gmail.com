import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypePrCharge } from 'app/shared/model/type-pr-charge.model';
import { TypePrChargeService } from './type-pr-charge.service';

@Component({
  templateUrl: './type-pr-charge-delete-dialog.component.html'
})
export class TypePrChargeDeleteDialogComponent {
  typePrCharge?: ITypePrCharge;

  constructor(
    protected typePrChargeService: TypePrChargeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typePrChargeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typePrChargeListModification');
      this.activeModal.close();
    });
  }
}
