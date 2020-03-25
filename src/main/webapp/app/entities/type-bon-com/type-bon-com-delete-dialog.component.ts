import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeBonCom } from 'app/shared/model/type-bon-com.model';
import { TypeBonComService } from './type-bon-com.service';

@Component({
  templateUrl: './type-bon-com-delete-dialog.component.html'
})
export class TypeBonComDeleteDialogComponent {
  typeBonCom?: ITypeBonCom;

  constructor(
    protected typeBonComService: TypeBonComService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeBonComService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeBonComListModification');
      this.activeModal.close();
    });
  }
}
