import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypePole, TypePole } from 'app/shared/model/type-pole.model';
import { TypePoleService } from './type-pole.service';

@Component({
  selector: 'jhi-type-pole-update',
  templateUrl: './type-pole-update.component.html'
})
export class TypePoleUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypePole: [],
    libelleTypePole: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typePoleService: TypePoleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typePole }) => {
      this.updateForm(typePole);
    });
  }

  updateForm(typePole: ITypePole): void {
    this.editForm.patchValue({
      id: typePole.id,
      codeTypePole: typePole.codeTypePole,
      libelleTypePole: typePole.libelleTypePole,
      dateCreated: typePole.dateCreated,
      dateUpdated: typePole.dateUpdated,
      userCreated: typePole.userCreated,
      userUpdated: typePole.userUpdated,
      userDeleted: typePole.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typePole = this.createFromForm();
    if (typePole.id !== undefined) {
      this.subscribeToSaveResponse(this.typePoleService.update(typePole));
    } else {
      this.subscribeToSaveResponse(this.typePoleService.create(typePole));
    }
  }

  private createFromForm(): ITypePole {
    return {
      ...new TypePole(),
      id: this.editForm.get(['id'])!.value,
      codeTypePole: this.editForm.get(['codeTypePole'])!.value,
      libelleTypePole: this.editForm.get(['libelleTypePole'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypePole>>): void {
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
