import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeMagasin, TypeMagasin } from 'app/shared/model/type-magasin.model';
import { TypeMagasinService } from './type-magasin.service';

@Component({
  selector: 'jhi-type-magasin-update',
  templateUrl: './type-magasin-update.component.html'
})
export class TypeMagasinUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypeMagasin: [],
    libelleTypeMagasin: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typeMagasinService: TypeMagasinService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeMagasin }) => {
      this.updateForm(typeMagasin);
    });
  }

  updateForm(typeMagasin: ITypeMagasin): void {
    this.editForm.patchValue({
      id: typeMagasin.id,
      codeTypeMagasin: typeMagasin.codeTypeMagasin,
      libelleTypeMagasin: typeMagasin.libelleTypeMagasin,
      dateCreated: typeMagasin.dateCreated,
      dateUpdated: typeMagasin.dateUpdated,
      userCreated: typeMagasin.userCreated,
      userUpdated: typeMagasin.userUpdated,
      userDeleted: typeMagasin.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeMagasin = this.createFromForm();
    if (typeMagasin.id !== undefined) {
      this.subscribeToSaveResponse(this.typeMagasinService.update(typeMagasin));
    } else {
      this.subscribeToSaveResponse(this.typeMagasinService.create(typeMagasin));
    }
  }

  private createFromForm(): ITypeMagasin {
    return {
      ...new TypeMagasin(),
      id: this.editForm.get(['id'])!.value,
      codeTypeMagasin: this.editForm.get(['codeTypeMagasin'])!.value,
      libelleTypeMagasin: this.editForm.get(['libelleTypeMagasin'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeMagasin>>): void {
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
