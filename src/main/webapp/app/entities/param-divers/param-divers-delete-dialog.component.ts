import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParamDivers } from 'app/shared/model/param-divers.model';
import { ParamDiversService } from './param-divers.service';

@Component({
  templateUrl: './param-divers-delete-dialog.component.html'
})
export class ParamDiversDeleteDialogComponent {
  paramDivers?: IParamDivers;

  constructor(
    protected paramDiversService: ParamDiversService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paramDiversService.delete(id).subscribe(() => {
      this.eventManager.broadcast('paramDiversListModification');
      this.activeModal.close();
    });
  }
}
