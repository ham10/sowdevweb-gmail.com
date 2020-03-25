import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeFacture, TypeFacture } from 'app/shared/model/type-facture.model';
import { TypeFactureService } from './type-facture.service';

@Component({
  selector: 'jhi-type-facture-update',
  templateUrl: './type-facture-update.component.html'
})
export class TypeFactureUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypeFacture: [],
    libelleTypeFacture: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typeFactureService: TypeFactureService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeFacture }) => {
      this.updateForm(typeFacture);
    });
  }

  updateForm(typeFacture: ITypeFacture): void {
    this.editForm.patchValue({
      id: typeFacture.id,
      codeTypeFacture: typeFacture.codeTypeFacture,
      libelleTypeFacture: typeFacture.libelleTypeFacture,
      dateCreated: typeFacture.dateCreated,
      dateUpdated: typeFacture.dateUpdated,
      userCreated: typeFacture.userCreated,
      userUpdated: typeFacture.userUpdated,
      userDeleted: typeFacture.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeFacture = this.createFromForm();
    if (typeFacture.id !== undefined) {
      this.subscribeToSaveResponse(this.typeFactureService.update(typeFacture));
    } else {
      this.subscribeToSaveResponse(this.typeFactureService.create(typeFacture));
    }
  }

  private createFromForm(): ITypeFacture {
    return {
      ...new TypeFacture(),
      id: this.editForm.get(['id'])!.value,
      codeTypeFacture: this.editForm.get(['codeTypeFacture'])!.value,
      libelleTypeFacture: this.editForm.get(['libelleTypeFacture'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeFacture>>): void {
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
