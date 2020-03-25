import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParamSys } from 'app/shared/model/param-sys.model';
import { ParamSysService } from './param-sys.service';

@Component({
  templateUrl: './param-sys-delete-dialog.component.html'
})
export class ParamSysDeleteDialogComponent {
  paramSys?: IParamSys;

  constructor(protected paramSysService: ParamSysService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paramSysService.delete(id).subscribe(() => {
      this.eventManager.broadcast('paramSysListModification');
      this.activeModal.close();
    });
  }
}
