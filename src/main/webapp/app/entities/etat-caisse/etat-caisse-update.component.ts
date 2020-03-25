import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEtatCaisse, EtatCaisse } from 'app/shared/model/etat-caisse.model';
import { EtatCaisseService } from './etat-caisse.service';

@Component({
  selector: 'jhi-etat-caisse-update',
  templateUrl: './etat-caisse-update.component.html'
})
export class EtatCaisseUpdateComponent implements OnInit {
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

  constructor(protected etatCaisseService: EtatCaisseService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etatCaisse }) => {
      this.updateForm(etatCaisse);
    });
  }

  updateForm(etatCaisse: IEtatCaisse): void {
    this.editForm.patchValue({
      id: etatCaisse.id,
      code: etatCaisse.code,
      libelle: etatCaisse.libelle,
      dateCreated: etatCaisse.dateCreated,
      dateUpdated: etatCaisse.dateUpdated,
      dateDeleted: etatCaisse.dateDeleted,
      userCreated: etatCaisse.userCreated,
      userUpdated: etatCaisse.userUpdated,
      userDeleted: etatCaisse.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etatCaisse = this.createFromForm();
    if (etatCaisse.id !== undefined) {
      this.subscribeToSaveResponse(this.etatCaisseService.update(etatCaisse));
    } else {
      this.subscribeToSaveResponse(this.etatCaisseService.create(etatCaisse));
    }
  }

  private createFromForm(): IEtatCaisse {
    return {
      ...new EtatCaisse(),
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtatCaisse>>): void {
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
