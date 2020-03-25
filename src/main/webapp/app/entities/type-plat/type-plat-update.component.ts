import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypePlat, TypePlat } from 'app/shared/model/type-plat.model';
import { TypePlatService } from './type-plat.service';

@Component({
  selector: 'jhi-type-plat-update',
  templateUrl: './type-plat-update.component.html'
})
export class TypePlatUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypePlat: [],
    libelleTypePlat: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typePlatService: TypePlatService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typePlat }) => {
      this.updateForm(typePlat);
    });
  }

  updateForm(typePlat: ITypePlat): void {
    this.editForm.patchValue({
      id: typePlat.id,
      codeTypePlat: typePlat.codeTypePlat,
      libelleTypePlat: typePlat.libelleTypePlat,
      dateCreated: typePlat.dateCreated,
      dateUpdated: typePlat.dateUpdated,
      userCreated: typePlat.userCreated,
      userUpdated: typePlat.userUpdated,
      userDeleted: typePlat.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typePlat = this.createFromForm();
    if (typePlat.id !== undefined) {
      this.subscribeToSaveResponse(this.typePlatService.update(typePlat));
    } else {
      this.subscribeToSaveResponse(this.typePlatService.create(typePlat));
    }
  }

  private createFromForm(): ITypePlat {
    return {
      ...new TypePlat(),
      id: this.editForm.get(['id'])!.value,
      codeTypePlat: this.editForm.get(['codeTypePlat'])!.value,
      libelleTypePlat: this.editForm.get(['libelleTypePlat'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypePlat>>): void {
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
