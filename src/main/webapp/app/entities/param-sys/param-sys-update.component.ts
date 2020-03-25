import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IParamSys, ParamSys } from 'app/shared/model/param-sys.model';
import { ParamSysService } from './param-sys.service';

@Component({
  selector: 'jhi-param-sys-update',
  templateUrl: './param-sys-update.component.html'
})
export class ParamSysUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeParamSys: [],
    libelleParamSys: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected paramSysService: ParamSysService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paramSys }) => {
      this.updateForm(paramSys);
    });
  }

  updateForm(paramSys: IParamSys): void {
    this.editForm.patchValue({
      id: paramSys.id,
      codeParamSys: paramSys.codeParamSys,
      libelleParamSys: paramSys.libelleParamSys,
      dateCreated: paramSys.dateCreated,
      dateUpdated: paramSys.dateUpdated,
      userCreated: paramSys.userCreated,
      userUpdated: paramSys.userUpdated,
      userDeleted: paramSys.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paramSys = this.createFromForm();
    if (paramSys.id !== undefined) {
      this.subscribeToSaveResponse(this.paramSysService.update(paramSys));
    } else {
      this.subscribeToSaveResponse(this.paramSysService.create(paramSys));
    }
  }

  private createFromForm(): IParamSys {
    return {
      ...new ParamSys(),
      id: this.editForm.get(['id'])!.value,
      codeParamSys: this.editForm.get(['codeParamSys'])!.value,
      libelleParamSys: this.editForm.get(['libelleParamSys'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParamSys>>): void {
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
