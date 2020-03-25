import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChapCompta } from 'app/shared/model/chap-compta.model';
import { ChapComptaService } from './chap-compta.service';

@Component({
  templateUrl: './chap-compta-delete-dialog.component.html'
})
export class ChapComptaDeleteDialogComponent {
  chapCompta?: IChapCompta;

  constructor(
    protected chapComptaService: ChapComptaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.chapComptaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('chapComptaListModification');
      this.activeModal.close();
    });
  }
}
