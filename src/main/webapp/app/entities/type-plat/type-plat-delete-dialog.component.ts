import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypePlat } from 'app/shared/model/type-plat.model';
import { TypePlatService } from './type-plat.service';

@Component({
  templateUrl: './type-plat-delete-dialog.component.html'
})
export class TypePlatDeleteDialogComponent {
  typePlat?: ITypePlat;

  constructor(protected typePlatService: TypePlatService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typePlatService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typePlatListModification');
      this.activeModal.close();
    });
  }
}
