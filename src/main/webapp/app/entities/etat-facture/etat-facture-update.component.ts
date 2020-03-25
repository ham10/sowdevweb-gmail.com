import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEtatFacture, EtatFacture } from 'app/shared/model/etat-facture.model';
import { EtatFactureService } from './etat-facture.service';

@Component({
  selector: 'jhi-etat-facture-update',
  templateUrl: './etat-facture-update.component.html'
})
export class EtatFactureUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected etatFactureService: EtatFactureService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etatFacture }) => {
      this.updateForm(etatFacture);
    });
  }

  updateForm(etatFacture: IEtatFacture): void {
    this.editForm.patchValue({
      id: etatFacture.id,
      code: etatFacture.code,
      libelle: etatFacture.libelle,
      dateCreated: etatFacture.dateCreated,
      dateUpdated: etatFacture.dateUpdated,
      dateDeleted: etatFacture.dateDeleted,
      userCreated: etatFacture.userCreated,
      userUpdated: etatFacture.userUpdated,
      userDeleted: etatFacture.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etatFacture = this.createFromForm();
    if (etatFacture.id !== undefined) {
      this.subscribeToSaveResponse(this.etatFactureService.update(etatFacture));
    } else {
      this.subscribeToSaveResponse(this.etatFactureService.create(etatFacture));
    }
  }

  private createFromForm(): IEtatFacture {
    return {
      ...new EtatFacture(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtatFacture>>): void {
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
