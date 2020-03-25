import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IReponse, Reponse } from 'app/shared/model/reponse.model';
import { ReponseService } from './reponse.service';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from 'app/entities/question/question.service';

@Component({
  selector: 'jhi-reponse-update',
  templateUrl: './reponse-update.component.html'
})
export class ReponseUpdateComponent implements OnInit {
  isSaving = false;
  questions: IQuestion[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    reponse: [],
    dateDeleted: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdate: [],
    userDelete: [],
    question: []
  });

  constructor(
    protected reponseService: ReponseService,
    protected questionService: QuestionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reponse }) => {
      if (!reponse.id) {
        const today = moment().startOf('day');
        reponse.dateDeleted = today;
        reponse.dateCreated = today;
        reponse.dateUpdated = today;
      }

      this.updateForm(reponse);

      this.questionService.query().subscribe((res: HttpResponse<IQuestion[]>) => (this.questions = res.body || []));
    });
  }

  updateForm(reponse: IReponse): void {
    this.editForm.patchValue({
      id: reponse.id,
      code: reponse.code,
      reponse: reponse.reponse,
      dateDeleted: reponse.dateDeleted ? reponse.dateDeleted.format(DATE_TIME_FORMAT) : null,
      dateCreated: reponse.dateCreated ? reponse.dateCreated.format(DATE_TIME_FORMAT) : null,
      dateUpdated: reponse.dateUpdated ? reponse.dateUpdated.format(DATE_TIME_FORMAT) : null,
      userCreated: reponse.userCreated,
      userUpdate: reponse.userUpdate,
      userDelete: reponse.userDelete,
      question: reponse.question
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reponse = this.createFromForm();
    if (reponse.id !== undefined) {
      this.subscribeToSaveResponse(this.reponseService.update(reponse));
    } else {
      this.subscribeToSaveResponse(this.reponseService.create(reponse));
    }
  }

  private createFromForm(): IReponse {
    return {
      ...new Reponse(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      reponse: this.editForm.get(['reponse'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value
        ? moment(this.editForm.get(['dateDeleted'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dateCreated: this.editForm.get(['dateCreated'])!.value
        ? moment(this.editForm.get(['dateCreated'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value
        ? moment(this.editForm.get(['dateUpdated'])!.value, DATE_TIME_FORMAT)
        : undefined,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdate: this.editForm.get(['userUpdate'])!.value,
      userDelete: this.editForm.get(['userDelete'])!.value,
      question: this.editForm.get(['question'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReponse>>): void {
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

  trackById(index: number, item: IQuestion): any {
    return item.id;
  }
}
