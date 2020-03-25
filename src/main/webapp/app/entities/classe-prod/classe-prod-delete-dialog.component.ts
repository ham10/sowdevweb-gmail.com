import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClasseProd } from 'app/shared/model/classe-prod.model';
import { ClasseProdService } from './classe-prod.service';

@Component({
  templateUrl: './classe-prod-delete-dialog.component.html'
})
export class ClasseProdDeleteDialogComponent {
  classeProd?: IClasseProd;

  constructor(
    protected classeProdService: ClasseProdService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.classeProdService.delete(id).subscribe(() => {
      this.eventManager.broadcast('classeProdListModification');
      this.activeModal.close();
    });
  }
}
