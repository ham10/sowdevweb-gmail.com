import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAyantDroit, AyantDroit } from 'app/shared/model/ayant-droit.model';
import { AyantDroitService } from './ayant-droit.service';
import { ITypePrCharge } from 'app/shared/model/type-pr-charge.model';
import { TypePrChargeService } from 'app/entities/type-pr-charge/type-pr-charge.service';

@Component({
  selector: 'jhi-ayant-droit-update',
  templateUrl: './ayant-droit-update.component.html'
})
export class AyantDroitUpdateComponent implements OnInit {
  isSaving = false;
  typeprcharges: ITypePrCharge[] = [];
  dateNaisDp: any;
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    code: [],
    prenom: [],
    nom: [],
    lienParente: [],
    dateNais: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    typePrCharge: []
  });

  constructor(
    protected ayantDroitService: AyantDroitService,
    protected typePrChargeService: TypePrChargeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ayantDroit }) => {
      this.updateForm(ayantDroit);

      this.typePrChargeService.query().subscribe((res: HttpResponse<ITypePrCharge[]>) => (this.typeprcharges = res.body || []));
    });
  }

  updateForm(ayantDroit: IAyantDroit): void {
    this.editForm.patchValue({
      id: ayantDroit.id,
      code: ayantDroit.code,
      prenom: ayantDroit.prenom,
      nom: ayantDroit.nom,
      lienParente: ayantDroit.lienParente,
      dateNais: ayantDroit.dateNais,
      dateCreated: ayantDroit.dateCreated,
      dateUpdated: ayantDroit.dateUpdated,
      dateDeleted: ayantDroit.dateDeleted,
      userCreated: ayantDroit.userCreated,
      userUpdated: ayantDroit.userUpdated,
      userDeleted: ayantDroit.userDeleted,
      typePrCharge: ayantDroit.typePrCharge
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ayantDroit = this.createFromForm();
    if (ayantDroit.id !== undefined) {
      this.subscribeToSaveResponse(this.ayantDroitService.update(ayantDroit));
    } else {
      this.subscribeToSaveResponse(this.ayantDroitService.create(ayantDroit));
    }
  }

  private createFromForm(): IAyantDroit {
    return {
      ...new AyantDroit(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      lienParente: this.editForm.get(['lienParente'])!.value,
      dateNais: this.editForm.get(['dateNais'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      typePrCharge: this.editForm.get(['typePrCharge'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAyantDroit>>): void {
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

  trackById(index: number, item: ITypePrCharge): any {
    return item.id;
  }
}
