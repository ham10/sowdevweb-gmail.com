import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEtatImmo, EtatImmo } from 'app/shared/model/etat-immo.model';
import { EtatImmoService } from './etat-immo.service';

@Component({
  selector: 'jhi-etat-immo-update',
  templateUrl: './etat-immo-update.component.html'
})
export class EtatImmoUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    codeEtat: [],
    libelleEtat: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected etatImmoService: EtatImmoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etatImmo }) => {
      this.updateForm(etatImmo);
    });
  }

  updateForm(etatImmo: IEtatImmo): void {
    this.editForm.patchValue({
      id: etatImmo.id,
      codeEtat: etatImmo.codeEtat,
      libelleEtat: etatImmo.libelleEtat,
      dateCreated: etatImmo.dateCreated,
      dateUpdated: etatImmo.dateUpdated,
      dateDeleted: etatImmo.dateDeleted,
      userCreated: etatImmo.userCreated,
      userUpdated: etatImmo.userUpdated,
      userDeleted: etatImmo.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etatImmo = this.createFromForm();
    if (etatImmo.id !== undefined) {
      this.subscribeToSaveResponse(this.etatImmoService.update(etatImmo));
    } else {
      this.subscribeToSaveResponse(this.etatImmoService.create(etatImmo));
    }
  }

  private createFromForm(): IEtatImmo {
    return {
      ...new EtatImmo(),
      id: this.editForm.get(['id'])!.value,
      codeEtat: this.editForm.get(['codeEtat'])!.value,
      libelleEtat: this.editForm.get(['libelleEtat'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtatImmo>>): void {
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
