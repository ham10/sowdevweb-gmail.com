import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICatReport } from 'app/shared/model/cat-report.model';
import { CatReportService } from './cat-report.service';

@Component({
  templateUrl: './cat-report-delete-dialog.component.html'
})
export class CatReportDeleteDialogComponent {
  catReport?: ICatReport;

  constructor(protected catReportService: CatReportService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.catReportService.delete(id).subscribe(() => {
      this.eventManager.broadcast('catReportListModification');
      this.activeModal.close();
    });
  }
}
