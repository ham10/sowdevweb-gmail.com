import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IImmo, Immo } from 'app/shared/model/immo.model';
import { ImmoService } from './immo.service';
import { IOperation } from 'app/shared/model/operation.model';
import { OperationService } from 'app/entities/operation/operation.service';
import { ITypeImmo } from 'app/shared/model/type-immo.model';
import { TypeImmoService } from 'app/entities/type-immo/type-immo.service';
import { ITabAmortis } from 'app/shared/model/tab-amortis.model';
import { TabAmortisService } from 'app/entities/tab-amortis/tab-amortis.service';

type SelectableEntity = IOperation | ITypeImmo | ITabAmortis;

@Component({
  selector: 'jhi-immo-update',
  templateUrl: './immo-update.component.html'
})
export class ImmoUpdateComponent implements OnInit {
  isSaving = false;
  operations: IOperation[] = [];
  typeimmos: ITypeImmo[] = [];
  tabamortis: ITabAmortis[] = [];
  dateAquisitionDp: any;
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    libelle: [],
    valeurAquisition: [],
    dateAquisition: [],
    valeurNetComptable: [],
    montant: [],
    duree: [],
    nbreEcheance: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    operation: [],
    typeImmo: [],
    tableauAmortissement: []
  });

  constructor(
    protected immoService: ImmoService,
    protected operationService: OperationService,
    protected typeImmoService: TypeImmoService,
    protected tabAmortisService: TabAmortisService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ immo }) => {
      this.updateForm(immo);

      this.operationService.query().subscribe((res: HttpResponse<IOperation[]>) => (this.operations = res.body || []));

      this.typeImmoService.query().subscribe((res: HttpResponse<ITypeImmo[]>) => (this.typeimmos = res.body || []));

      this.tabAmortisService.query().subscribe((res: HttpResponse<ITabAmortis[]>) => (this.tabamortis = res.body || []));
    });
  }

  updateForm(immo: IImmo): void {
    this.editForm.patchValue({
      id: immo.id,
      libelle: immo.libelle,
      valeurAquisition: immo.valeurAquisition,
      dateAquisition: immo.dateAquisition,
      valeurNetComptable: immo.valeurNetComptable,
      montant: immo.montant,
      duree: immo.duree,
      nbreEcheance: immo.nbreEcheance,
      dateCreated: immo.dateCreated,
      dateUpdated: immo.dateUpdated,
      dateDeleted: immo.dateDeleted,
      userCreated: immo.userCreated,
      userUpdated: immo.userUpdated,
      userDeleted: immo.userDeleted,
      operation: immo.operation,
      typeImmo: immo.typeImmo,
      tableauAmortissement: immo.tableauAmortissement
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const immo = this.createFromForm();
    if (immo.id !== undefined) {
      this.subscribeToSaveResponse(this.immoService.update(immo));
    } else {
      this.subscribeToSaveResponse(this.immoService.create(immo));
    }
  }

  private createFromForm(): IImmo {
    return {
      ...new Immo(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      valeurAquisition: this.editForm.get(['valeurAquisition'])!.value,
      dateAquisition: this.editForm.get(['dateAquisition'])!.value,
      valeurNetComptable: this.editForm.get(['valeurNetComptable'])!.value,
      montant: this.editForm.get(['montant'])!.value,
      duree: this.editForm.get(['duree'])!.value,
      nbreEcheance: this.editForm.get(['nbreEcheance'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      operation: this.editForm.get(['operation'])!.value,
      typeImmo: this.editForm.get(['typeImmo'])!.value,
      tableauAmortissement: this.editForm.get(['tableauAmortissement'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImmo>>): void {
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
