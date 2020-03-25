import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMachAutorise } from 'app/shared/model/mach-autorise.model';
import { MachAutoriseService } from './mach-autorise.service';

@Component({
  templateUrl: './mach-autorise-delete-dialog.component.html'
})
export class MachAutoriseDeleteDialogComponent {
  machAutorise?: IMachAutorise;

  constructor(
    protected machAutoriseService: MachAutoriseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.machAutoriseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('machAutoriseListModification');
      this.activeModal.close();
    });
  }
}
