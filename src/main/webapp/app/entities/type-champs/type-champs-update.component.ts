import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeChamps, TypeChamps } from 'app/shared/model/type-champs.model';
import { TypeChampsService } from './type-champs.service';

@Component({
  selector: 'jhi-type-champs-update',
  templateUrl: './type-champs-update.component.html'
})
export class TypeChampsUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypeChamps: [],
    libelleTypeChamps: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typeChampsService: TypeChampsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeChamps }) => {
      this.updateForm(typeChamps);
    });
  }

  updateForm(typeChamps: ITypeChamps): void {
    this.editForm.patchValue({
      id: typeChamps.id,
      codeTypeChamps: typeChamps.codeTypeChamps,
      libelleTypeChamps: typeChamps.libelleTypeChamps,
      dateCreated: typeChamps.dateCreated,
      dateUpdated: typeChamps.dateUpdated,
      userCreated: typeChamps.userCreated,
      userUpdated: typeChamps.userUpdated,
      userDeleted: typeChamps.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeChamps = this.createFromForm();
    if (typeChamps.id !== undefined) {
      this.subscribeToSaveResponse(this.typeChampsService.update(typeChamps));
    } else {
      this.subscribeToSaveResponse(this.typeChampsService.create(typeChamps));
    }
  }

  private createFromForm(): ITypeChamps {
    return {
      ...new TypeChamps(),
      id: this.editForm.get(['id'])!.value,
      codeTypeChamps: this.editForm.get(['codeTypeChamps'])!.value,
      libelleTypeChamps: this.editForm.get(['libelleTypeChamps'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeChamps>>): void {
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
