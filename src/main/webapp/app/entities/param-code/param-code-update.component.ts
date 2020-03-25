import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IParamCode, ParamCode } from 'app/shared/model/param-code.model';
import { ParamCodeService } from './param-code.service';

@Component({
  selector: 'jhi-param-code-update',
  templateUrl: './param-code-update.component.html'
})
export class ParamCodeUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeParamCode: [],
    libelleParamCode: [],
    descriptionParamCode: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected paramCodeService: ParamCodeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paramCode }) => {
      this.updateForm(paramCode);
    });
  }

  updateForm(paramCode: IParamCode): void {
    this.editForm.patchValue({
      id: paramCode.id,
      codeParamCode: paramCode.codeParamCode,
      libelleParamCode: paramCode.libelleParamCode,
      descriptionParamCode: paramCode.descriptionParamCode,
      dateCreated: paramCode.dateCreated,
      dateUpdated: paramCode.dateUpdated,
      userCreated: paramCode.userCreated,
      userUpdated: paramCode.userUpdated,
      userDeleted: paramCode.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paramCode = this.createFromForm();
    if (paramCode.id !== undefined) {
      this.subscribeToSaveResponse(this.paramCodeService.update(paramCode));
    } else {
      this.subscribeToSaveResponse(this.paramCodeService.create(paramCode));
    }
  }

  private createFromForm(): IParamCode {
    return {
      ...new ParamCode(),
      id: this.editForm.get(['id'])!.value,
      codeParamCode: this.editForm.get(['codeParamCode'])!.value,
      libelleParamCode: this.editForm.get(['libelleParamCode'])!.value,
      descriptionParamCode: this.editForm.get(['descriptionParamCode'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParamCode>>): void {
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
