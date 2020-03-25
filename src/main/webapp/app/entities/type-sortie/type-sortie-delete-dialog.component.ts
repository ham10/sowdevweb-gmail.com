import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeSortie } from 'app/shared/model/type-sortie.model';
import { TypeSortieService } from './type-sortie.service';

@Component({
  templateUrl: './type-sortie-delete-dialog.component.html'
})
export class TypeSortieDeleteDialogComponent {
  typeSortie?: ITypeSortie;

  constructor(
    protected typeSortieService: TypeSortieService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeSortieService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeSortieListModification');
      this.activeModal.close();
    });
  }
}
