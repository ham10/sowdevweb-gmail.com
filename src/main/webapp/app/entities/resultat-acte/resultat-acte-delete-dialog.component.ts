import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResultatActe } from 'app/shared/model/resultat-acte.model';
import { ResultatActeService } from './resultat-acte.service';

@Component({
  templateUrl: './resultat-acte-delete-dialog.component.html'
})
export class ResultatActeDeleteDialogComponent {
  resultatActe?: IResultatActe;

  constructor(
    protected resultatActeService: ResultatActeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resultatActeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('resultatActeListModification');
      this.activeModal.close();
    });
  }
}
