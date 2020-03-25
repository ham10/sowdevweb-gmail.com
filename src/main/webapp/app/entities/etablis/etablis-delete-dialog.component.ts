import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEtablis } from 'app/shared/model/etablis.model';
import { EtablisService } from './etablis.service';

@Component({
  templateUrl: './etablis-delete-dialog.component.html'
})
export class EtablisDeleteDialogComponent {
  etablis?: IEtablis;

  constructor(protected etablisService: EtablisService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.etablisService.delete(id).subscribe(() => {
      this.eventManager.broadcast('etablisListModification');
      this.activeModal.close();
    });
  }
}
