import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeLit, TypeLit } from 'app/shared/model/type-lit.model';
import { TypeLitService } from './type-lit.service';

@Component({
  selector: 'jhi-type-lit-update',
  templateUrl: './type-lit-update.component.html'
})
export class TypeLitUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypeLit: [],
    libelleTypeLit: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typeLitService: TypeLitService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeLit }) => {
      this.updateForm(typeLit);
    });
  }

  updateForm(typeLit: ITypeLit): void {
    this.editForm.patchValue({
      id: typeLit.id,
      codeTypeLit: typeLit.codeTypeLit,
      libelleTypeLit: typeLit.libelleTypeLit,
      dateCreated: typeLit.dateCreated,
      dateUpdated: typeLit.dateUpdated,
      userCreated: typeLit.userCreated,
      userUpdated: typeLit.userUpdated,
      userDeleted: typeLit.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeLit = this.createFromForm();
    if (typeLit.id !== undefined) {
      this.subscribeToSaveResponse(this.typeLitService.update(typeLit));
    } else {
      this.subscribeToSaveResponse(this.typeLitService.create(typeLit));
    }
  }

  private createFromForm(): ITypeLit {
    return {
      ...new TypeLit(),
      id: this.editForm.get(['id'])!.value,
      codeTypeLit: this.editForm.get(['codeTypeLit'])!.value,
      libelleTypeLit: this.editForm.get(['libelleTypeLit'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeLit>>): void {
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
