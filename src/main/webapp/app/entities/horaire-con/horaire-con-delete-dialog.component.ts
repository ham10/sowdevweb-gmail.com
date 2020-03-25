import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHoraireCon } from 'app/shared/model/horaire-con.model';
import { HoraireConService } from './horaire-con.service';

@Component({
  templateUrl: './horaire-con-delete-dialog.component.html'
})
export class HoraireConDeleteDialogComponent {
  horaireCon?: IHoraireCon;

  constructor(
    protected horaireConService: HoraireConService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.horaireConService.delete(id).subscribe(() => {
      this.eventManager.broadcast('horaireConListModification');
      this.activeModal.close();
    });
  }
}
