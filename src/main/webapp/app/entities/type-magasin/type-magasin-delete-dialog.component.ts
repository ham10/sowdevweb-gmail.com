import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeMagasin } from 'app/shared/model/type-magasin.model';
import { TypeMagasinService } from './type-magasin.service';

@Component({
  templateUrl: './type-magasin-delete-dialog.component.html'
})
export class TypeMagasinDeleteDialogComponent {
  typeMagasin?: ITypeMagasin;

  constructor(
    protected typeMagasinService: TypeMagasinService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeMagasinService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeMagasinListModification');
      this.activeModal.close();
    });
  }
}
