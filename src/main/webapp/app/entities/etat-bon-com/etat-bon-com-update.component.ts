import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEtatBonCom, EtatBonCom } from 'app/shared/model/etat-bon-com.model';
import { EtatBonComService } from './etat-bon-com.service';

@Component({
  selector: 'jhi-etat-bon-com-update',
  templateUrl: './etat-bon-com-update.component.html'
})
export class EtatBonComUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelle: [],
    code: []
  });

  constructor(protected etatBonComService: EtatBonComService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etatBonCom }) => {
      this.updateForm(etatBonCom);
    });
  }

  updateForm(etatBonCom: IEtatBonCom): void {
    this.editForm.patchValue({
      id: etatBonCom.id,
      libelle: etatBonCom.libelle,
      code: etatBonCom.code
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etatBonCom = this.createFromForm();
    if (etatBonCom.id !== undefined) {
      this.subscribeToSaveResponse(this.etatBonComService.update(etatBonCom));
    } else {
      this.subscribeToSaveResponse(this.etatBonComService.create(etatBonCom));
    }
  }

  private createFromForm(): IEtatBonCom {
    return {
      ...new EtatBonCom(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      code: this.editForm.get(['code'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtatBonCom>>): void {
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
