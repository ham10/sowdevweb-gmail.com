import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEtatPlanning, EtatPlanning } from 'app/shared/model/etat-planning.model';
import { EtatPlanningService } from './etat-planning.service';

@Component({
  selector: 'jhi-etat-planning-update',
  templateUrl: './etat-planning-update.component.html'
})
export class EtatPlanningUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: []
  });

  constructor(protected etatPlanningService: EtatPlanningService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etatPlanning }) => {
      this.updateForm(etatPlanning);
    });
  }

  updateForm(etatPlanning: IEtatPlanning): void {
    this.editForm.patchValue({
      id: etatPlanning.id,
      code: etatPlanning.code,
      libelle: etatPlanning.libelle
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etatPlanning = this.createFromForm();
    if (etatPlanning.id !== undefined) {
      this.subscribeToSaveResponse(this.etatPlanningService.update(etatPlanning));
    } else {
      this.subscribeToSaveResponse(this.etatPlanningService.create(etatPlanning));
    }
  }

  private createFromForm(): IEtatPlanning {
    return {
      ...new EtatPlanning(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtatPlanning>>): void {
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
