import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeSortie, TypeSortie } from 'app/shared/model/type-sortie.model';
import { TypeSortieService } from './type-sortie.service';

@Component({
  selector: 'jhi-type-sortie-update',
  templateUrl: './type-sortie-update.component.html'
})
export class TypeSortieUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypeSortie: [],
    libelleTypeSortie: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typeSortieService: TypeSortieService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeSortie }) => {
      this.updateForm(typeSortie);
    });
  }

  updateForm(typeSortie: ITypeSortie): void {
    this.editForm.patchValue({
      id: typeSortie.id,
      codeTypeSortie: typeSortie.codeTypeSortie,
      libelleTypeSortie: typeSortie.libelleTypeSortie,
      dateCreated: typeSortie.dateCreated,
      dateUpdated: typeSortie.dateUpdated,
      userCreated: typeSortie.userCreated,
      userUpdated: typeSortie.userUpdated,
      userDeleted: typeSortie.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeSortie = this.createFromForm();
    if (typeSortie.id !== undefined) {
      this.subscribeToSaveResponse(this.typeSortieService.update(typeSortie));
    } else {
      this.subscribeToSaveResponse(this.typeSortieService.create(typeSortie));
    }
  }

  private createFromForm(): ITypeSortie {
    return {
      ...new TypeSortie(),
      id: this.editForm.get(['id'])!.value,
      codeTypeSortie: this.editForm.get(['codeTypeSortie'])!.value,
      libelleTypeSortie: this.editForm.get(['libelleTypeSortie'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeSortie>>): void {
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
