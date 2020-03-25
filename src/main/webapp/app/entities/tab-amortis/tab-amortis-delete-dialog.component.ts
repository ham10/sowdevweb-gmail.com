import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITabAmortis } from 'app/shared/model/tab-amortis.model';
import { TabAmortisService } from './tab-amortis.service';

@Component({
  templateUrl: './tab-amortis-delete-dialog.component.html'
})
export class TabAmortisDeleteDialogComponent {
  tabAmortis?: ITabAmortis;

  constructor(
    protected tabAmortisService: TabAmortisService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tabAmortisService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tabAmortisListModification');
      this.activeModal.close();
    });
  }
}
