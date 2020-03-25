import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParamCode } from 'app/shared/model/param-code.model';
import { ParamCodeService } from './param-code.service';

@Component({
  templateUrl: './param-code-delete-dialog.component.html'
})
export class ParamCodeDeleteDialogComponent {
  paramCode?: IParamCode;

  constructor(protected paramCodeService: ParamCodeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paramCodeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('paramCodeListModification');
      this.activeModal.close();
    });
  }
}
