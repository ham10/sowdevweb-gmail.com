import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEcriture, Ecriture } from 'app/shared/model/ecriture.model';
import { EcritureService } from './ecriture.service';
import { ICompte } from 'app/shared/model/compte.model';
import { CompteService } from 'app/entities/compte/compte.service';
import { IOperation } from 'app/shared/model/operation.model';
import { OperationService } from 'app/entities/operation/operation.service';

type SelectableEntity = ICompte | IOperation;

@Component({
  selector: 'jhi-ecriture-update',
  templateUrl: './ecriture-update.component.html'
})
export class EcritureUpdateComponent implements OnInit {
  isSaving = false;
  comptes: ICompte[] = [];
  operations: IOperation[] = [];
  dateDp: any;
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    libelle: [],
    date: [],
    montant: [],
    sens: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    compte: [],
    operation: []
  });

  constructor(
    protected ecritureService: EcritureService,
    protected compteService: CompteService,
    protected operationService: OperationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ecriture }) => {
      this.updateForm(ecriture);

      this.compteService.query().subscribe((res: HttpResponse<ICompte[]>) => (this.comptes = res.body || []));

      this.operationService.query().subscribe((res: HttpResponse<IOperation[]>) => (this.operations = res.body || []));
    });
  }

  updateForm(ecriture: IEcriture): void {
    this.editForm.patchValue({
      id: ecriture.id,
      libelle: ecriture.libelle,
      date: ecriture.date,
      montant: ecriture.montant,
      sens: ecriture.sens,
      dateCreated: ecriture.dateCreated,
      dateUpdated: ecriture.dateUpdated,
      dateDeleted: ecriture.dateDeleted,
      userCreated: ecriture.userCreated,
      userUpdated: ecriture.userUpdated,
      userDeleted: ecriture.userDeleted,
      compte: ecriture.compte,
      operation: ecriture.operation
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ecriture = this.createFromForm();
    if (ecriture.id !== undefined) {
      this.subscribeToSaveResponse(this.ecritureService.update(ecriture));
    } else {
      this.subscribeToSaveResponse(this.ecritureService.create(ecriture));
    }
  }

  private createFromForm(): IEcriture {
    return {
      ...new Ecriture(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      date: this.editForm.get(['date'])!.value,
      montant: this.editForm.get(['montant'])!.value,
      sens: this.editForm.get(['sens'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      compte: this.editForm.get(['compte'])!.value,
      operation: this.editForm.get(['operation'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEcriture>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
