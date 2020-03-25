import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeDoc, TypeDoc } from 'app/shared/model/type-doc.model';
import { TypeDocService } from './type-doc.service';

@Component({
  selector: 'jhi-type-doc-update',
  templateUrl: './type-doc-update.component.html'
})
export class TypeDocUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypeDoc: [],
    libelleTypeDoc: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typeDocService: TypeDocService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeDoc }) => {
      this.updateForm(typeDoc);
    });
  }

  updateForm(typeDoc: ITypeDoc): void {
    this.editForm.patchValue({
      id: typeDoc.id,
      codeTypeDoc: typeDoc.codeTypeDoc,
      libelleTypeDoc: typeDoc.libelleTypeDoc,
      dateCreated: typeDoc.dateCreated,
      dateUpdated: typeDoc.dateUpdated,
      userCreated: typeDoc.userCreated,
      userUpdated: typeDoc.userUpdated,
      userDeleted: typeDoc.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeDoc = this.createFromForm();
    if (typeDoc.id !== undefined) {
      this.subscribeToSaveResponse(this.typeDocService.update(typeDoc));
    } else {
      this.subscribeToSaveResponse(this.typeDocService.create(typeDoc));
    }
  }

  private createFromForm(): ITypeDoc {
    return {
      ...new TypeDoc(),
      id: this.editForm.get(['id'])!.value,
      codeTypeDoc: this.editForm.get(['codeTypeDoc'])!.value,
      libelleTypeDoc: this.editForm.get(['libelleTypeDoc'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeDoc>>): void {
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
