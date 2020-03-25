import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeAntecedent } from 'app/shared/model/type-antecedent.model';
import { TypeAntecedentService } from './type-antecedent.service';

@Component({
  templateUrl: './type-antecedent-delete-dialog.component.html'
})
export class TypeAntecedentDeleteDialogComponent {
  typeAntecedent?: ITypeAntecedent;

  constructor(
    protected typeAntecedentService: TypeAntecedentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeAntecedentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeAntecedentListModification');
      this.activeModal.close();
    });
  }
}
