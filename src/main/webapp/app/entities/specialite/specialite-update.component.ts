import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISpecialite, Specialite } from 'app/shared/model/specialite.model';
import { SpecialiteService } from './specialite.service';

@Component({
  selector: 'jhi-specialite-update',
  templateUrl: './specialite-update.component.html'
})
export class SpecialiteUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeSpecialite: [],
    libelleSpecialite: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected specialiteService: SpecialiteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ specialite }) => {
      this.updateForm(specialite);
    });
  }

  updateForm(specialite: ISpecialite): void {
    this.editForm.patchValue({
      id: specialite.id,
      codeSpecialite: specialite.codeSpecialite,
      libelleSpecialite: specialite.libelleSpecialite,
      dateCreated: specialite.dateCreated,
      dateUpdated: specialite.dateUpdated,
      userCreated: specialite.userCreated,
      userUpdated: specialite.userUpdated,
      userDeleted: specialite.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const specialite = this.createFromForm();
    if (specialite.id !== undefined) {
      this.subscribeToSaveResponse(this.specialiteService.update(specialite));
    } else {
      this.subscribeToSaveResponse(this.specialiteService.create(specialite));
    }
  }

  private createFromForm(): ISpecialite {
    return {
      ...new Specialite(),
      id: this.editForm.get(['id'])!.value,
      codeSpecialite: this.editForm.get(['codeSpecialite'])!.value,
      libelleSpecialite: this.editForm.get(['libelleSpecialite'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpecialite>>): void {
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
