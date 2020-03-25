import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeCaisse, TypeCaisse } from 'app/shared/model/type-caisse.model';
import { TypeCaisseService } from './type-caisse.service';

@Component({
  selector: 'jhi-type-caisse-update',
  templateUrl: './type-caisse-update.component.html'
})
export class TypeCaisseUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypeCaisse: [],
    libelleTypeCaisse: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typeCaisseService: TypeCaisseService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeCaisse }) => {
      this.updateForm(typeCaisse);
    });
  }

  updateForm(typeCaisse: ITypeCaisse): void {
    this.editForm.patchValue({
      id: typeCaisse.id,
      codeTypeCaisse: typeCaisse.codeTypeCaisse,
      libelleTypeCaisse: typeCaisse.libelleTypeCaisse,
      dateCreated: typeCaisse.dateCreated,
      dateUpdated: typeCaisse.dateUpdated,
      userCreated: typeCaisse.userCreated,
      userUpdated: typeCaisse.userUpdated,
      userDeleted: typeCaisse.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeCaisse = this.createFromForm();
    if (typeCaisse.id !== undefined) {
      this.subscribeToSaveResponse(this.typeCaisseService.update(typeCaisse));
    } else {
      this.subscribeToSaveResponse(this.typeCaisseService.create(typeCaisse));
    }
  }

  private createFromForm(): ITypeCaisse {
    return {
      ...new TypeCaisse(),
      id: this.editForm.get(['id'])!.value,
      codeTypeCaisse: this.editForm.get(['codeTypeCaisse'])!.value,
      libelleTypeCaisse: this.editForm.get(['libelleTypeCaisse'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeCaisse>>): void {
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
