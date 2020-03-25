import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypePlateau, TypePlateau } from 'app/shared/model/type-plateau.model';
import { TypePlateauService } from './type-plateau.service';

@Component({
  selector: 'jhi-type-plateau-update',
  templateUrl: './type-plateau-update.component.html'
})
export class TypePlateauUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypePlateau: [],
    libelleTypePlateau: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typePlateauService: TypePlateauService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typePlateau }) => {
      this.updateForm(typePlateau);
    });
  }

  updateForm(typePlateau: ITypePlateau): void {
    this.editForm.patchValue({
      id: typePlateau.id,
      codeTypePlateau: typePlateau.codeTypePlateau,
      libelleTypePlateau: typePlateau.libelleTypePlateau,
      dateCreated: typePlateau.dateCreated,
      dateUpdated: typePlateau.dateUpdated,
      userCreated: typePlateau.userCreated,
      userUpdated: typePlateau.userUpdated,
      userDeleted: typePlateau.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typePlateau = this.createFromForm();
    if (typePlateau.id !== undefined) {
      this.subscribeToSaveResponse(this.typePlateauService.update(typePlateau));
    } else {
      this.subscribeToSaveResponse(this.typePlateauService.create(typePlateau));
    }
  }

  private createFromForm(): ITypePlateau {
    return {
      ...new TypePlateau(),
      id: this.editForm.get(['id'])!.value,
      codeTypePlateau: this.editForm.get(['codeTypePlateau'])!.value,
      libelleTypePlateau: this.editForm.get(['libelleTypePlateau'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypePlateau>>): void {
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
