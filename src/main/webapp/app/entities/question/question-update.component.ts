import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IQuestion, Question } from 'app/shared/model/question.model';
import { QuestionService } from './question.service';
import { IQuestionnaire } from 'app/shared/model/questionnaire.model';
import { QuestionnaireService } from 'app/entities/questionnaire/questionnaire.service';
import { ITypeQuestion } from 'app/shared/model/type-question.model';
import { TypeQuestionService } from 'app/entities/type-question/type-question.service';

type SelectableEntity = IQuestionnaire | ITypeQuestion;

@Component({
  selector: 'jhi-question-update',
  templateUrl: './question-update.component.html'
})
export class QuestionUpdateComponent implements OnInit {
  isSaving = false;
  questionnaires: IQuestionnaire[] = [];
  typequestions: ITypeQuestion[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: [],
    description: [],
    obligatoire: [],
    dateDeleted: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdate: [],
    userDelete: [],
    questionnaire: [],
    typeQuestion: []
  });

  constructor(
    protected questionService: QuestionService,
    protected questionnaireService: QuestionnaireService,
    protected typeQuestionService: TypeQuestionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ question }) => {
      if (!question.id) {
        const today = moment().startOf('day');
        question.dateDeleted = today;
        question.dateCreated = today;
        question.dateUpdated = today;
      }

      this.updateForm(question);

      this.questionnaireService.query().subscribe((res: HttpResponse<IQuestionnaire[]>) => (this.questionnaires = res.body || []));

      this.typeQuestionService.query().subscribe((res: HttpResponse<ITypeQuestion[]>) => (this.typequestions = res.body || []));
    });
  }

  updateForm(question: IQuestion): void {
    this.editForm.patchValue({
      id: question.id,
      code: question.code,
      libelle: question.libelle,
      description: question.description,
      obligatoire: question.obligatoire,
      dateDeleted: question.dateDeleted ? question.dateDeleted.format(DATE_TIME_FORMAT) : null,
      dateCreated: question.dateCreated ? question.dateCreated.format(DATE_TIME_FORMAT) : null,
      dateUpdated: question.dateUpdated ? question.dateUpdated.format(DATE_TIME_FORMAT) : null,
      userCreated: question.userCreated,
      userUpdate: question.userUpdate,
      userDelete: question.userDelete,
      questionnaire: question.questionnaire,
      typeQuestion: question.typeQuestion
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const question = this.createFromForm();
    if (question.id !== undefined) {
      this.subscribeToSaveResponse(this.questionService.update(question));
    } else {
      this.subscribeToSaveResponse(this.questionService.create(question));
    }
  }

  private createFromForm(): IQuestion {
    return {
      ...new Question(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      description: this.editForm.get(['description'])!.value,
      obligatoire: this.editForm.get(['obligatoire'])!.value,
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
      questionnaire: this.editForm.get(['questionnaire'])!.value,
      typeQuestion: this.editForm.get(['typeQuestion'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestion>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
