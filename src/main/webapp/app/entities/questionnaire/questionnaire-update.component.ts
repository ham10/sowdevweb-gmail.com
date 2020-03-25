import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IQuestionnaire, Questionnaire } from 'app/shared/model/questionnaire.model';
import { QuestionnaireService } from './questionnaire.service';

@Component({
  selector: 'jhi-questionnaire-update',
  templateUrl: './questionnaire-update.component.html'
})
export class QuestionnaireUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: [],
    dateDeleted: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdate: [],
    userDelete: []
  });

  constructor(protected questionnaireService: QuestionnaireService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionnaire }) => {
      if (!questionnaire.id) {
        const today = moment().startOf('day');
        questionnaire.dateDeleted = today;
        questionnaire.dateCreated = today;
        questionnaire.dateUpdated = today;
      }

      this.updateForm(questionnaire);
    });
  }

  updateForm(questionnaire: IQuestionnaire): void {
    this.editForm.patchValue({
      id: questionnaire.id,
      code: questionnaire.code,
      libelle: questionnaire.libelle,
      dateDeleted: questionnaire.dateDeleted ? questionnaire.dateDeleted.format(DATE_TIME_FORMAT) : null,
      dateCreated: questionnaire.dateCreated ? questionnaire.dateCreated.format(DATE_TIME_FORMAT) : null,
      dateUpdated: questionnaire.dateUpdated ? questionnaire.dateUpdated.format(DATE_TIME_FORMAT) : null,
      userCreated: questionnaire.userCreated,
      userUpdate: questionnaire.userUpdate,
      userDelete: questionnaire.userDelete
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const questionnaire = this.createFromForm();
    if (questionnaire.id !== undefined) {
      this.subscribeToSaveResponse(this.questionnaireService.update(questionnaire));
    } else {
      this.subscribeToSaveResponse(this.questionnaireService.create(questionnaire));
    }
  }

  private createFromForm(): IQuestionnaire {
    return {
      ...new Questionnaire(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
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
      userDelete: this.editForm.get(['userDelete'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionnaire>>): void {
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
