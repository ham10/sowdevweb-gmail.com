import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IJourFerie, JourFerie } from 'app/shared/model/jour-ferie.model';
import { JourFerieService } from './jour-ferie.service';

@Component({
  selector: 'jhi-jour-ferie-update',
  templateUrl: './jour-ferie-update.component.html'
})
export class JourFerieUpdateComponent implements OnInit {
  isSaving = false;
  dateJourFerieDp: any;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    libelleJourFerie: [],
    dateJourFerie: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected jourFerieService: JourFerieService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jourFerie }) => {
      this.updateForm(jourFerie);
    });
  }

  updateForm(jourFerie: IJourFerie): void {
    this.editForm.patchValue({
      id: jourFerie.id,
      libelleJourFerie: jourFerie.libelleJourFerie,
      dateJourFerie: jourFerie.dateJourFerie,
      dateCreated: jourFerie.dateCreated,
      dateUpdated: jourFerie.dateUpdated,
      userCreated: jourFerie.userCreated,
      userUpdated: jourFerie.userUpdated,
      userDeleted: jourFerie.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jourFerie = this.createFromForm();
    if (jourFerie.id !== undefined) {
      this.subscribeToSaveResponse(this.jourFerieService.update(jourFerie));
    } else {
      this.subscribeToSaveResponse(this.jourFerieService.create(jourFerie));
    }
  }

  private createFromForm(): IJourFerie {
    return {
      ...new JourFerie(),
      id: this.editForm.get(['id'])!.value,
      libelleJourFerie: this.editForm.get(['libelleJourFerie'])!.value,
      dateJourFerie: this.editForm.get(['dateJourFerie'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJourFerie>>): void {
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
