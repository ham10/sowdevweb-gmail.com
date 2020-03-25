import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICivilite, Civilite } from 'app/shared/model/civilite.model';
import { CiviliteService } from './civilite.service';

@Component({
  selector: 'jhi-civilite-update',
  templateUrl: './civilite-update.component.html'
})
export class CiviliteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelleCivilite: [],
    intituleCivilite: []
  });

  constructor(protected civiliteService: CiviliteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ civilite }) => {
      this.updateForm(civilite);
    });
  }

  updateForm(civilite: ICivilite): void {
    this.editForm.patchValue({
      id: civilite.id,
      libelleCivilite: civilite.libelleCivilite,
      intituleCivilite: civilite.intituleCivilite
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const civilite = this.createFromForm();
    if (civilite.id !== undefined) {
      this.subscribeToSaveResponse(this.civiliteService.update(civilite));
    } else {
      this.subscribeToSaveResponse(this.civiliteService.create(civilite));
    }
  }

  private createFromForm(): ICivilite {
    return {
      ...new Civilite(),
      id: this.editForm.get(['id'])!.value,
      libelleCivilite: this.editForm.get(['libelleCivilite'])!.value,
      intituleCivilite: this.editForm.get(['intituleCivilite'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICivilite>>): void {
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
