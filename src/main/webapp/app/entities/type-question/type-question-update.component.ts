import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITypeQuestion, TypeQuestion } from 'app/shared/model/type-question.model';
import { TypeQuestionService } from './type-question.service';

@Component({
  selector: 'jhi-type-question-update',
  templateUrl: './type-question-update.component.html'
})
export class TypeQuestionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    belleType: [],
    multipliciteChoix: [],
    dateDeleted: [],
    dateCreated: [],
    dateUpdated: [],
    userUpdate: [],
    userDelete: []
  });

  constructor(protected typeQuestionService: TypeQuestionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeQuestion }) => {
      if (!typeQuestion.id) {
        const today = moment().startOf('day');
        typeQuestion.dateDeleted = today;
        typeQuestion.dateCreated = today;
        typeQuestion.dateUpdated = today;
      }

      this.updateForm(typeQuestion);
    });
  }

  updateForm(typeQuestion: ITypeQuestion): void {
    this.editForm.patchValue({
      id: typeQuestion.id,
      code: typeQuestion.code,
      belleType: typeQuestion.belleType,
      multipliciteChoix: typeQuestion.multipliciteChoix,
      dateDeleted: typeQuestion.dateDeleted ? typeQuestion.dateDeleted.format(DATE_TIME_FORMAT) : null,
      dateCreated: typeQuestion.dateCreated ? typeQuestion.dateCreated.format(DATE_TIME_FORMAT) : null,
      dateUpdated: typeQuestion.dateUpdated ? typeQuestion.dateUpdated.format(DATE_TIME_FORMAT) : null,
      userUpdate: typeQuestion.userUpdate,
      userDelete: typeQuestion.userDelete
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeQuestion = this.createFromForm();
    if (typeQuestion.id !== undefined) {
      this.subscribeToSaveResponse(this.typeQuestionService.update(typeQuestion));
    } else {
      this.subscribeToSaveResponse(this.typeQuestionService.create(typeQuestion));
    }
  }

  private createFromForm(): ITypeQuestion {
    return {
      ...new TypeQuestion(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      belleType: this.editForm.get(['belleType'])!.value,
      multipliciteChoix: this.editForm.get(['multipliciteChoix'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value
        ? moment(this.editForm.get(['dateDeleted'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dateCreated: this.editForm.get(['dateCreated'])!.value
        ? moment(this.editForm.get(['dateCreated'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value
        ? moment(this.editForm.get(['dateUpdated'])!.value, DATE_TIME_FORMAT)
        : undefined,
      userUpdate: this.editForm.get(['userUpdate'])!.value,
      userDelete: this.editForm.get(['userDelete'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeQuestion>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
