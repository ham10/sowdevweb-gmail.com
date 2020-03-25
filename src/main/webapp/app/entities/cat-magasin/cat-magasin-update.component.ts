import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICatMagasin, CatMagasin } from 'app/shared/model/cat-magasin.model';
import { CatMagasinService } from './cat-magasin.service';

@Component({
  selector: 'jhi-cat-magasin-update',
  templateUrl: './cat-magasin-update.component.html'
})
export class CatMagasinUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeCatMagasin: [],
    libelleCatMagasin: [],
    descriptionCatMagasin: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected catMagasinService: CatMagasinService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catMagasin }) => {
      this.updateForm(catMagasin);
    });
  }

  updateForm(catMagasin: ICatMagasin): void {
    this.editForm.patchValue({
      id: catMagasin.id,
      codeCatMagasin: catMagasin.codeCatMagasin,
      libelleCatMagasin: catMagasin.libelleCatMagasin,
      descriptionCatMagasin: catMagasin.descriptionCatMagasin,
      dateCreated: catMagasin.dateCreated,
      dateUpdated: catMagasin.dateUpdated,
      userCreated: catMagasin.userCreated,
      userUpdated: catMagasin.userUpdated,
      userDeleted: catMagasin.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const catMagasin = this.createFromForm();
    if (catMagasin.id !== undefined) {
      this.subscribeToSaveResponse(this.catMagasinService.update(catMagasin));
    } else {
      this.subscribeToSaveResponse(this.catMagasinService.create(catMagasin));
    }
  }

  private createFromForm(): ICatMagasin {
    return {
      ...new CatMagasin(),
      id: this.editForm.get(['id'])!.value,
      codeCatMagasin: this.editForm.get(['codeCatMagasin'])!.value,
      libelleCatMagasin: this.editForm.get(['libelleCatMagasin'])!.value,
      descriptionCatMagasin: this.editForm.get(['descriptionCatMagasin'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatMagasin>>): void {
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
