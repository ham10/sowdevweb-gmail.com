import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeConstante, TypeConstante } from 'app/shared/model/type-constante.model';
import { TypeConstanteService } from './type-constante.service';

@Component({
  selector: 'jhi-type-constante-update',
  templateUrl: './type-constante-update.component.html'
})
export class TypeConstanteUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypeConstante: [],
    libelleTypeConstante: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typeConstanteService: TypeConstanteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeConstante }) => {
      this.updateForm(typeConstante);
    });
  }

  updateForm(typeConstante: ITypeConstante): void {
    this.editForm.patchValue({
      id: typeConstante.id,
      codeTypeConstante: typeConstante.codeTypeConstante,
      libelleTypeConstante: typeConstante.libelleTypeConstante,
      dateCreated: typeConstante.dateCreated,
      dateUpdated: typeConstante.dateUpdated,
      userCreated: typeConstante.userCreated,
      userUpdated: typeConstante.userUpdated,
      userDeleted: typeConstante.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeConstante = this.createFromForm();
    if (typeConstante.id !== undefined) {
      this.subscribeToSaveResponse(this.typeConstanteService.update(typeConstante));
    } else {
      this.subscribeToSaveResponse(this.typeConstanteService.create(typeConstante));
    }
  }

  private createFromForm(): ITypeConstante {
    return {
      ...new TypeConstante(),
      id: this.editForm.get(['id'])!.value,
      codeTypeConstante: this.editForm.get(['codeTypeConstante'])!.value,
      libelleTypeConstante: this.editForm.get(['libelleTypeConstante'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeConstante>>): void {
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
