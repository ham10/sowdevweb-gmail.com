import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeAntecedent, TypeAntecedent } from 'app/shared/model/type-antecedent.model';
import { TypeAntecedentService } from './type-antecedent.service';

@Component({
  selector: 'jhi-type-antecedent-update',
  templateUrl: './type-antecedent-update.component.html'
})
export class TypeAntecedentUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypeAntecedent: [],
    libelleTypeAntecedent: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typeAntecedentService: TypeAntecedentService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeAntecedent }) => {
      this.updateForm(typeAntecedent);
    });
  }

  updateForm(typeAntecedent: ITypeAntecedent): void {
    this.editForm.patchValue({
      id: typeAntecedent.id,
      codeTypeAntecedent: typeAntecedent.codeTypeAntecedent,
      libelleTypeAntecedent: typeAntecedent.libelleTypeAntecedent,
      dateCreated: typeAntecedent.dateCreated,
      dateUpdated: typeAntecedent.dateUpdated,
      userCreated: typeAntecedent.userCreated,
      userUpdated: typeAntecedent.userUpdated,
      userDeleted: typeAntecedent.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeAntecedent = this.createFromForm();
    if (typeAntecedent.id !== undefined) {
      this.subscribeToSaveResponse(this.typeAntecedentService.update(typeAntecedent));
    } else {
      this.subscribeToSaveResponse(this.typeAntecedentService.create(typeAntecedent));
    }
  }

  private createFromForm(): ITypeAntecedent {
    return {
      ...new TypeAntecedent(),
      id: this.editForm.get(['id'])!.value,
      codeTypeAntecedent: this.editForm.get(['codeTypeAntecedent'])!.value,
      libelleTypeAntecedent: this.editForm.get(['libelleTypeAntecedent'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeAntecedent>>): void {
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
