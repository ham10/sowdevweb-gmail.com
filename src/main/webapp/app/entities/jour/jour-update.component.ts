import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IJour, Jour } from 'app/shared/model/jour.model';
import { JourService } from './jour.service';

@Component({
  selector: 'jhi-jour-update',
  templateUrl: './jour-update.component.html'
})
export class JourUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    libelleJour: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected jourService: JourService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jour }) => {
      this.updateForm(jour);
    });
  }

  updateForm(jour: IJour): void {
    this.editForm.patchValue({
      id: jour.id,
      libelleJour: jour.libelleJour,
      dateCreated: jour.dateCreated,
      dateUpdated: jour.dateUpdated,
      userCreated: jour.userCreated,
      userUpdated: jour.userUpdated,
      userDeleted: jour.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jour = this.createFromForm();
    if (jour.id !== undefined) {
      this.subscribeToSaveResponse(this.jourService.update(jour));
    } else {
      this.subscribeToSaveResponse(this.jourService.create(jour));
    }
  }

  private createFromForm(): IJour {
    return {
      ...new Jour(),
      id: this.editForm.get(['id'])!.value,
      libelleJour: this.editForm.get(['libelleJour'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJour>>): void {
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
