import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFormeProd } from 'app/shared/model/forme-prod.model';
import { FormeProdService } from './forme-prod.service';

@Component({
  templateUrl: './forme-prod-delete-dialog.component.html'
})
export class FormeProdDeleteDialogComponent {
  formeProd?: IFormeProd;

  constructor(protected formeProdService: FormeProdService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.formeProdService.delete(id).subscribe(() => {
      this.eventManager.broadcast('formeProdListModification');
      this.activeModal.close();
    });
  }
}
