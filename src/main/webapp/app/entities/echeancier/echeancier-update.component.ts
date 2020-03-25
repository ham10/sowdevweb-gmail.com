import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEcheancier, Echeancier } from 'app/shared/model/echeancier.model';
import { EcheancierService } from './echeancier.service';
import { IFacture } from 'app/shared/model/facture.model';
import { FactureService } from 'app/entities/facture/facture.service';

@Component({
  selector: 'jhi-echeancier-update',
  templateUrl: './echeancier-update.component.html'
})
export class EcheancierUpdateComponent implements OnInit {
  isSaving = false;
  factures: IFacture[] = [];
  dateDp: any;
  datePaiementDp: any;
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    code: [],
    numero: [],
    date: [],
    datePaiement: [],
    montant: [],
    montantPaye: [],
    capital: [],
    frais: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    facture: []
  });

  constructor(
    protected echeancierService: EcheancierService,
    protected factureService: FactureService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ echeancier }) => {
      this.updateForm(echeancier);

      this.factureService.query().subscribe((res: HttpResponse<IFacture[]>) => (this.factures = res.body || []));
    });
  }

  updateForm(echeancier: IEcheancier): void {
    this.editForm.patchValue({
      id: echeancier.id,
      code: echeancier.code,
      numero: echeancier.numero,
      date: echeancier.date,
      datePaiement: echeancier.datePaiement,
      montant: echeancier.montant,
      montantPaye: echeancier.montantPaye,
      capital: echeancier.capital,
      frais: echeancier.frais,
      dateCreated: echeancier.dateCreated,
      dateUpdated: echeancier.dateUpdated,
      dateDeleted: echeancier.dateDeleted,
      userCreated: echeancier.userCreated,
      userUpdated: echeancier.userUpdated,
      userDeleted: echeancier.userDeleted,
      facture: echeancier.facture
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const echeancier = this.createFromForm();
    if (echeancier.id !== undefined) {
      this.subscribeToSaveResponse(this.echeancierService.update(echeancier));
    } else {
      this.subscribeToSaveResponse(this.echeancierService.create(echeancier));
    }
  }

  private createFromForm(): IEcheancier {
    return {
      ...new Echeancier(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      date: this.editForm.get(['date'])!.value,
      datePaiement: this.editForm.get(['datePaiement'])!.value,
      montant: this.editForm.get(['montant'])!.value,
      montantPaye: this.editForm.get(['montantPaye'])!.value,
      capital: this.editForm.get(['capital'])!.value,
      frais: this.editForm.get(['frais'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      facture: this.editForm.get(['facture'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEcheancier>>): void {
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

  trackById(index: number, item: IFacture): any {
    return item.id;
  }
}
