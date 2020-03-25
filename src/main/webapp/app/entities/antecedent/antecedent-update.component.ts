import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAntecedent, Antecedent } from 'app/shared/model/antecedent.model';
import { AntecedentService } from './antecedent.service';

@Component({
  selector: 'jhi-antecedent-update',
  templateUrl: './antecedent-update.component.html'
})
export class AntecedentUpdateComponent implements OnInit {
  isSaving = false;
  dateDp: any;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    libelle: [],
    description: [],
    date: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected antecedentService: AntecedentService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ antecedent }) => {
      this.updateForm(antecedent);
    });
  }

  updateForm(antecedent: IAntecedent): void {
    this.editForm.patchValue({
      id: antecedent.id,
      libelle: antecedent.libelle,
      description: antecedent.description,
      date: antecedent.date,
      dateCreated: antecedent.dateCreated,
      dateUpdated: antecedent.dateUpdated,
      userCreated: antecedent.userCreated,
      userUpdated: antecedent.userUpdated,
      userDeleted: antecedent.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const antecedent = this.createFromForm();
    if (antecedent.id !== undefined) {
      this.subscribeToSaveResponse(this.antecedentService.update(antecedent));
    } else {
      this.subscribeToSaveResponse(this.antecedentService.create(antecedent));
    }
  }

  private createFromForm(): IAntecedent {
    return {
      ...new Antecedent(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      description: this.editForm.get(['description'])!.value,
      date: this.editForm.get(['date'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAntecedent>>): void {
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
