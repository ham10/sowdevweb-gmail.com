import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IParamDivers, ParamDivers } from 'app/shared/model/param-divers.model';
import { ParamDiversService } from './param-divers.service';

@Component({
  selector: 'jhi-param-divers-update',
  templateUrl: './param-divers-update.component.html'
})
export class ParamDiversUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeParamDivers: [],
    libelleParamDivers: [],
    descriptionParamDivers: [],
    valeurNum1: [],
    valeurNum2: [],
    valeurNum3: [],
    valeurText1: [],
    valeurText2: [],
    valeurText3: [],
    valeurBoolean1: [],
    valeurBoolean2: [],
    valeurBoolean3: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected paramDiversService: ParamDiversService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paramDivers }) => {
      this.updateForm(paramDivers);
    });
  }

  updateForm(paramDivers: IParamDivers): void {
    this.editForm.patchValue({
      id: paramDivers.id,
      codeParamDivers: paramDivers.codeParamDivers,
      libelleParamDivers: paramDivers.libelleParamDivers,
      descriptionParamDivers: paramDivers.descriptionParamDivers,
      valeurNum1: paramDivers.valeurNum1,
      valeurNum2: paramDivers.valeurNum2,
      valeurNum3: paramDivers.valeurNum3,
      valeurText1: paramDivers.valeurText1,
      valeurText2: paramDivers.valeurText2,
      valeurText3: paramDivers.valeurText3,
      valeurBoolean1: paramDivers.valeurBoolean1,
      valeurBoolean2: paramDivers.valeurBoolean2,
      valeurBoolean3: paramDivers.valeurBoolean3,
      dateCreated: paramDivers.dateCreated,
      dateUpdated: paramDivers.dateUpdated,
      userCreated: paramDivers.userCreated,
      userUpdated: paramDivers.userUpdated,
      userDeleted: paramDivers.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paramDivers = this.createFromForm();
    if (paramDivers.id !== undefined) {
      this.subscribeToSaveResponse(this.paramDiversService.update(paramDivers));
    } else {
      this.subscribeToSaveResponse(this.paramDiversService.create(paramDivers));
    }
  }

  private createFromForm(): IParamDivers {
    return {
      ...new ParamDivers(),
      id: this.editForm.get(['id'])!.value,
      codeParamDivers: this.editForm.get(['codeParamDivers'])!.value,
      libelleParamDivers: this.editForm.get(['libelleParamDivers'])!.value,
      descriptionParamDivers: this.editForm.get(['descriptionParamDivers'])!.value,
      valeurNum1: this.editForm.get(['valeurNum1'])!.value,
      valeurNum2: this.editForm.get(['valeurNum2'])!.value,
      valeurNum3: this.editForm.get(['valeurNum3'])!.value,
      valeurText1: this.editForm.get(['valeurText1'])!.value,
      valeurText2: this.editForm.get(['valeurText2'])!.value,
      valeurText3: this.editForm.get(['valeurText3'])!.value,
      valeurBoolean1: this.editForm.get(['valeurBoolean1'])!.value,
      valeurBoolean2: this.editForm.get(['valeurBoolean2'])!.value,
      valeurBoolean3: this.editForm.get(['valeurBoolean3'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParamDivers>>): void {
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
