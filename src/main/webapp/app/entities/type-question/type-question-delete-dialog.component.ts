import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeQuestion } from 'app/shared/model/type-question.model';
import { TypeQuestionService } from './type-question.service';

@Component({
  templateUrl: './type-question-delete-dialog.component.html'
})
export class TypeQuestionDeleteDialogComponent {
  typeQuestion?: ITypeQuestion;

  constructor(
    protected typeQuestionService: TypeQuestionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeQuestionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeQuestionListModification');
      this.activeModal.close();
    });
  }
}
