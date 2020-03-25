import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGroupeSan } from 'app/shared/model/groupe-san.model';
import { GroupeSanService } from './groupe-san.service';

@Component({
  templateUrl: './groupe-san-delete-dialog.component.html'
})
export class GroupeSanDeleteDialogComponent {
  groupeSan?: IGroupeSan;

  constructor(protected groupeSanService: GroupeSanService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.groupeSanService.delete(id).subscribe(() => {
      this.eventManager.broadcast('groupeSanListModification');
      this.activeModal.close();
    });
  }
}
