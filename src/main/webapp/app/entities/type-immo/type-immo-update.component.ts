import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeImmo, TypeImmo } from 'app/shared/model/type-immo.model';
import { TypeImmoService } from './type-immo.service';

@Component({
  selector: 'jhi-type-immo-update',
  templateUrl: './type-immo-update.component.html'
})
export class TypeImmoUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    code: [],
    modeCalcul: [],
    taux: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typeImmoService: TypeImmoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeImmo }) => {
      this.updateForm(typeImmo);
    });
  }

  updateForm(typeImmo: ITypeImmo): void {
    this.editForm.patchValue({
      id: typeImmo.id,
      code: typeImmo.code,
      modeCalcul: typeImmo.modeCalcul,
      taux: typeImmo.taux,
      dateCreated: typeImmo.dateCreated,
      dateUpdated: typeImmo.dateUpdated,
      dateDeleted: typeImmo.dateDeleted,
      userCreated: typeImmo.userCreated,
      userUpdated: typeImmo.userUpdated,
      userDeleted: typeImmo.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeImmo = this.createFromForm();
    if (typeImmo.id !== undefined) {
      this.subscribeToSaveResponse(this.typeImmoService.update(typeImmo));
    } else {
      this.subscribeToSaveResponse(this.typeImmoService.create(typeImmo));
    }
  }

  private createFromForm(): ITypeImmo {
    return {
      ...new TypeImmo(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      modeCalcul: this.editForm.get(['modeCalcul'])!.value,
      taux: this.editForm.get(['taux'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeImmo>>): void {
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
