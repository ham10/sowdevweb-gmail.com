import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISousFamille } from 'app/shared/model/sous-famille.model';
import { SousFamilleService } from './sous-famille.service';

@Component({
  templateUrl: './sous-famille-delete-dialog.component.html'
})
export class SousFamilleDeleteDialogComponent {
  sousFamille?: ISousFamille;

  constructor(
    protected sousFamilleService: SousFamilleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sousFamilleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sousFamilleListModification');
      this.activeModal.close();
    });
  }
}
