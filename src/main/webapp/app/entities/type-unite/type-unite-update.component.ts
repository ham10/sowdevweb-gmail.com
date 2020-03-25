import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeUnite, TypeUnite } from 'app/shared/model/type-unite.model';
import { TypeUniteService } from './type-unite.service';

@Component({
  selector: 'jhi-type-unite-update',
  templateUrl: './type-unite-update.component.html'
})
export class TypeUniteUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypeUnite: [],
    libelleTypeUnite: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typeUniteService: TypeUniteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeUnite }) => {
      this.updateForm(typeUnite);
    });
  }

  updateForm(typeUnite: ITypeUnite): void {
    this.editForm.patchValue({
      id: typeUnite.id,
      codeTypeUnite: typeUnite.codeTypeUnite,
      libelleTypeUnite: typeUnite.libelleTypeUnite,
      dateCreated: typeUnite.dateCreated,
      dateUpdated: typeUnite.dateUpdated,
      userCreated: typeUnite.userCreated,
      userUpdated: typeUnite.userUpdated,
      userDeleted: typeUnite.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeUnite = this.createFromForm();
    if (typeUnite.id !== undefined) {
      this.subscribeToSaveResponse(this.typeUniteService.update(typeUnite));
    } else {
      this.subscribeToSaveResponse(this.typeUniteService.create(typeUnite));
    }
  }

  private createFromForm(): ITypeUnite {
    return {
      ...new TypeUnite(),
      id: this.editForm.get(['id'])!.value,
      codeTypeUnite: this.editForm.get(['codeTypeUnite'])!.value,
      libelleTypeUnite: this.editForm.get(['libelleTypeUnite'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeUnite>>): void {
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
