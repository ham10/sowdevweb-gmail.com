import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeServices } from 'app/shared/model/type-services.model';
import { TypeServicesService } from './type-services.service';

@Component({
  templateUrl: './type-services-delete-dialog.component.html'
})
export class TypeServicesDeleteDialogComponent {
  typeServices?: ITypeServices;

  constructor(
    protected typeServicesService: TypeServicesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeServicesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeServicesListModification');
      this.activeModal.close();
    });
  }
}
