import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeReport } from 'app/shared/model/type-report.model';
import { TypeReportService } from './type-report.service';

@Component({
  templateUrl: './type-report-delete-dialog.component.html'
})
export class TypeReportDeleteDialogComponent {
  typeReport?: ITypeReport;

  constructor(
    protected typeReportService: TypeReportService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeReportService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeReportListModification');
      this.activeModal.close();
    });
  }
}
