import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeNotif, TypeNotif } from 'app/shared/model/type-notif.model';
import { TypeNotifService } from './type-notif.service';

@Component({
  selector: 'jhi-type-notif-update',
  templateUrl: './type-notif-update.component.html'
})
export class TypeNotifUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypeNotif: [],
    libelleTypeNotif: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typeNotifService: TypeNotifService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeNotif }) => {
      this.updateForm(typeNotif);
    });
  }

  updateForm(typeNotif: ITypeNotif): void {
    this.editForm.patchValue({
      id: typeNotif.id,
      codeTypeNotif: typeNotif.codeTypeNotif,
      libelleTypeNotif: typeNotif.libelleTypeNotif,
      dateCreated: typeNotif.dateCreated,
      dateUpdated: typeNotif.dateUpdated,
      userCreated: typeNotif.userCreated,
      userUpdated: typeNotif.userUpdated,
      userDeleted: typeNotif.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeNotif = this.createFromForm();
    if (typeNotif.id !== undefined) {
      this.subscribeToSaveResponse(this.typeNotifService.update(typeNotif));
    } else {
      this.subscribeToSaveResponse(this.typeNotifService.create(typeNotif));
    }
  }

  private createFromForm(): ITypeNotif {
    return {
      ...new TypeNotif(),
      id: this.editForm.get(['id'])!.value,
      codeTypeNotif: this.editForm.get(['codeTypeNotif'])!.value,
      libelleTypeNotif: this.editForm.get(['libelleTypeNotif'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeNotif>>): void {
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
