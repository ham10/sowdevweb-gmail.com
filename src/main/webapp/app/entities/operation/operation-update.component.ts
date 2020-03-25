import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOperation, Operation } from 'app/shared/model/operation.model';
import { OperationService } from './operation.service';
import { IEtatOperation } from 'app/shared/model/etat-operation.model';
import { EtatOperationService } from 'app/entities/etat-operation/etat-operation.service';
import { ICaisse } from 'app/shared/model/caisse.model';
import { CaisseService } from 'app/entities/caisse/caisse.service';
import { IFacture } from 'app/shared/model/facture.model';
import { FactureService } from 'app/entities/facture/facture.service';

type SelectableEntity = IEtatOperation | ICaisse | IFacture;

@Component({
  selector: 'jhi-operation-update',
  templateUrl: './operation-update.component.html'
})
export class OperationUpdateComponent implements OnInit {
  isSaving = false;
  etatoperations: IEtatOperation[] = [];
  caisses: ICaisse[] = [];
  factures: IFacture[] = [];
  dateDp: any;
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    libelle: [],
    date: [],
    montant: [],
    taxe: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    etatOperation: [],
    caisse: [],
    operation: []
  });

  constructor(
    protected operationService: OperationService,
    protected etatOperationService: EtatOperationService,
    protected caisseService: CaisseService,
    protected factureService: FactureService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ operation }) => {
      this.updateForm(operation);

      this.etatOperationService.query().subscribe((res: HttpResponse<IEtatOperation[]>) => (this.etatoperations = res.body || []));

      this.caisseService.query().subscribe((res: HttpResponse<ICaisse[]>) => (this.caisses = res.body || []));

      this.factureService.query().subscribe((res: HttpResponse<IFacture[]>) => (this.factures = res.body || []));
    });
  }

  updateForm(operation: IOperation): void {
    this.editForm.patchValue({
      id: operation.id,
      libelle: operation.libelle,
      date: operation.date,
      montant: operation.montant,
      taxe: operation.taxe,
      dateCreated: operation.dateCreated,
      dateUpdated: operation.dateUpdated,
      dateDeleted: operation.dateDeleted,
      userCreated: operation.userCreated,
      userUpdated: operation.userUpdated,
      userDeleted: operation.userDeleted,
      etatOperation: operation.etatOperation,
      caisse: operation.caisse,
      operation: operation.operation
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const operation = this.createFromForm();
    if (operation.id !== undefined) {
      this.subscribeToSaveResponse(this.operationService.update(operation));
    } else {
      this.subscribeToSaveResponse(this.operationService.create(operation));
    }
  }

  private createFromForm(): IOperation {
    return {
      ...new Operation(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      date: this.editForm.get(['date'])!.value,
      montant: this.editForm.get(['montant'])!.value,
      taxe: this.editForm.get(['taxe'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      etatOperation: this.editForm.get(['etatOperation'])!.value,
      caisse: this.editForm.get(['caisse'])!.value,
      operation: this.editForm.get(['operation'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOperation>>): void {
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
