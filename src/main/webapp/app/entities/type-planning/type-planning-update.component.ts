import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypePlanning, TypePlanning } from 'app/shared/model/type-planning.model';
import { TypePlanningService } from './type-planning.service';

@Component({
  selector: 'jhi-type-planning-update',
  templateUrl: './type-planning-update.component.html'
})
export class TypePlanningUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: []
  });

  constructor(protected typePlanningService: TypePlanningService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typePlanning }) => {
      this.updateForm(typePlanning);
    });
  }

  updateForm(typePlanning: ITypePlanning): void {
    this.editForm.patchValue({
      id: typePlanning.id,
      code: typePlanning.code,
      libelle: typePlanning.libelle
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typePlanning = this.createFromForm();
    if (typePlanning.id !== undefined) {
      this.subscribeToSaveResponse(this.typePlanningService.update(typePlanning));
    } else {
      this.subscribeToSaveResponse(this.typePlanningService.create(typePlanning));
    }
  }

  private createFromForm(): ITypePlanning {
    return {
      ...new TypePlanning(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypePlanning>>): void {
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
