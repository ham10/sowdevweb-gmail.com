import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeNotif } from 'app/shared/model/type-notif.model';
import { TypeNotifService } from './type-notif.service';

@Component({
  templateUrl: './type-notif-delete-dialog.component.html'
})
export class TypeNotifDeleteDialogComponent {
  typeNotif?: ITypeNotif;

  constructor(protected typeNotifService: TypeNotifService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeNotifService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeNotifListModification');
      this.activeModal.close();
    });
  }
}
