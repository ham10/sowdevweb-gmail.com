import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAnnotation, Annotation } from 'app/shared/model/annotation.model';
import { AnnotationService } from './annotation.service';
import { IQuestionnaire } from 'app/shared/model/questionnaire.model';
import { QuestionnaireService } from 'app/entities/questionnaire/questionnaire.service';

@Component({
  selector: 'jhi-annotation-update',
  templateUrl: './annotation-update.component.html'
})
export class AnnotationUpdateComponent implements OnInit {
  isSaving = false;
  questionnaires: IQuestionnaire[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    note: [],
    observation: [],
    nbQuestions: [],
    moyenne: [],
    dateDeleted: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdate: [],
    userDelete: [],
    questionnaire: []
  });

  constructor(
    protected annotationService: AnnotationService,
    protected questionnaireService: QuestionnaireService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ annotation }) => {
      if (!annotation.id) {
        const today = moment().startOf('day');
        annotation.dateDeleted = today;
        annotation.dateCreated = today;
        annotation.dateUpdated = today;
      }

      this.updateForm(annotation);

      this.questionnaireService.query().subscribe((res: HttpResponse<IQuestionnaire[]>) => (this.questionnaires = res.body || []));
    });
  }

  updateForm(annotation: IAnnotation): void {
    this.editForm.patchValue({
      id: annotation.id,
      code: annotation.code,
      note: annotation.note,
      observation: annotation.observation,
      nbQuestions: annotation.nbQuestions,
      moyenne: annotation.moyenne,
      dateDeleted: annotation.dateDeleted ? annotation.dateDeleted.format(DATE_TIME_FORMAT) : null,
      dateCreated: annotation.dateCreated ? annotation.dateCreated.format(DATE_TIME_FORMAT) : null,
      dateUpdated: annotation.dateUpdated ? annotation.dateUpdated.format(DATE_TIME_FORMAT) : null,
      userCreated: annotation.userCreated,
      userUpdate: annotation.userUpdate,
      userDelete: annotation.userDelete,
      questionnaire: annotation.questionnaire
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const annotation = this.createFromForm();
    if (annotation.id !== undefined) {
      this.subscribeToSaveResponse(this.annotationService.update(annotation));
    } else {
      this.subscribeToSaveResponse(this.annotationService.create(annotation));
    }
  }

  private createFromForm(): IAnnotation {
    return {
      ...new Annotation(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      note: this.editForm.get(['note'])!.value,
      observation: this.editForm.get(['observation'])!.value,
      nbQuestions: this.editForm.get(['nbQuestions'])!.value,
      moyenne: this.editForm.get(['moyenne'])!.value,
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
      questionnaire: this.editForm.get(['questionnaire'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnnotation>>): void {
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

  trackById(index: number, item: IQuestionnaire): any {
    return item.id;
  }
}
