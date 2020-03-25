import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEtatRdv, EtatRdv } from 'app/shared/model/etat-rdv.model';
import { EtatRdvService } from './etat-rdv.service';

@Component({
  selector: 'jhi-etat-rdv-update',
  templateUrl: './etat-rdv-update.component.html'
})
export class EtatRdvUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: []
  });

  constructor(protected etatRdvService: EtatRdvService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etatRdv }) => {
      this.updateForm(etatRdv);
    });
  }

  updateForm(etatRdv: IEtatRdv): void {
    this.editForm.patchValue({
      id: etatRdv.id,
      code: etatRdv.code,
      libelle: etatRdv.libelle
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etatRdv = this.createFromForm();
    if (etatRdv.id !== undefined) {
      this.subscribeToSaveResponse(this.etatRdvService.update(etatRdv));
    } else {
      this.subscribeToSaveResponse(this.etatRdvService.create(etatRdv));
    }
  }

  private createFromForm(): IEtatRdv {
    return {
      ...new EtatRdv(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtatRdv>>): void {
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
