import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAntecedent } from 'app/shared/model/antecedent.model';
import { AntecedentService } from './antecedent.service';

@Component({
  templateUrl: './antecedent-delete-dialog.component.html'
})
export class AntecedentDeleteDialogComponent {
  antecedent?: IAntecedent;

  constructor(
    protected antecedentService: AntecedentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.antecedentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('antecedentListModification');
      this.activeModal.close();
    });
  }
}
