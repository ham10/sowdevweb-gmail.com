import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypePiece } from 'app/shared/model/type-piece.model';
import { TypePieceService } from './type-piece.service';

@Component({
  templateUrl: './type-piece-delete-dialog.component.html'
})
export class TypePieceDeleteDialogComponent {
  typePiece?: ITypePiece;

  constructor(protected typePieceService: TypePieceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typePieceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typePieceListModification');
      this.activeModal.close();
    });
  }
}
