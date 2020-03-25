import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEtatBonCom } from 'app/shared/model/etat-bon-com.model';
import { EtatBonComService } from './etat-bon-com.service';

@Component({
  templateUrl: './etat-bon-com-delete-dialog.component.html'
})
export class EtatBonComDeleteDialogComponent {
  etatBonCom?: IEtatBonCom;

  constructor(
    protected etatBonComService: EtatBonComService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.etatBonComService.delete(id).subscribe(() => {
      this.eventManager.broadcast('etatBonComListModification');
      this.activeModal.close();
    });
  }
}
