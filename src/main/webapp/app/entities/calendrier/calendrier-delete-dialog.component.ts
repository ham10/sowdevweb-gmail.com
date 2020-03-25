import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICalendrier } from 'app/shared/model/calendrier.model';
import { CalendrierService } from './calendrier.service';

@Component({
  templateUrl: './calendrier-delete-dialog.component.html'
})
export class CalendrierDeleteDialogComponent {
  calendrier?: ICalendrier;

  constructor(
    protected calendrierService: CalendrierService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.calendrierService.delete(id).subscribe(() => {
      this.eventManager.broadcast('calendrierListModification');
      this.activeModal.close();
    });
  }
}
