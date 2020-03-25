import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeConstante } from 'app/shared/model/type-constante.model';
import { TypeConstanteService } from './type-constante.service';

@Component({
  templateUrl: './type-constante-delete-dialog.component.html'
})
export class TypeConstanteDeleteDialogComponent {
  typeConstante?: ITypeConstante;

  constructor(
    protected typeConstanteService: TypeConstanteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeConstanteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeConstanteListModification');
      this.activeModal.close();
    });
  }
}
