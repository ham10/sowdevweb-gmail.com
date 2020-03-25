import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICodeBudget } from 'app/shared/model/code-budget.model';
import { CodeBudgetService } from './code-budget.service';

@Component({
  templateUrl: './code-budget-delete-dialog.component.html'
})
export class CodeBudgetDeleteDialogComponent {
  codeBudget?: ICodeBudget;

  constructor(
    protected codeBudgetService: CodeBudgetService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.codeBudgetService.delete(id).subscribe(() => {
      this.eventManager.broadcast('codeBudgetListModification');
      this.activeModal.close();
    });
  }
}
