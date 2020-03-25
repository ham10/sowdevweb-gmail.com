import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeProd, TypeProd } from 'app/shared/model/type-prod.model';
import { TypeProdService } from './type-prod.service';

@Component({
  selector: 'jhi-type-prod-update',
  templateUrl: './type-prod-update.component.html'
})
export class TypeProdUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypeProd: [],
    libelleTypeProd: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typeProdService: TypeProdService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeProd }) => {
      this.updateForm(typeProd);
    });
  }

  updateForm(typeProd: ITypeProd): void {
    this.editForm.patchValue({
      id: typeProd.id,
      codeTypeProd: typeProd.codeTypeProd,
      libelleTypeProd: typeProd.libelleTypeProd,
      dateCreated: typeProd.dateCreated,
      dateUpdated: typeProd.dateUpdated,
      userCreated: typeProd.userCreated,
      userUpdated: typeProd.userUpdated,
      userDeleted: typeProd.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeProd = this.createFromForm();
    if (typeProd.id !== undefined) {
      this.subscribeToSaveResponse(this.typeProdService.update(typeProd));
    } else {
      this.subscribeToSaveResponse(this.typeProdService.create(typeProd));
    }
  }

  private createFromForm(): ITypeProd {
    return {
      ...new TypeProd(),
      id: this.editForm.get(['id'])!.value,
      codeTypeProd: this.editForm.get(['codeTypeProd'])!.value,
      libelleTypeProd: this.editForm.get(['libelleTypeProd'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeProd>>): void {
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
