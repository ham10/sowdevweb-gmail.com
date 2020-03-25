import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMouvement, Mouvement } from 'app/shared/model/mouvement.model';
import { MouvementService } from './mouvement.service';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit/produit.service';
import { ITypeMvtStock } from 'app/shared/model/type-mvt-stock.model';
import { TypeMvtStockService } from 'app/entities/type-mvt-stock/type-mvt-stock.service';

type SelectableEntity = IProduit | ITypeMvtStock;

@Component({
  selector: 'jhi-mouvement-update',
  templateUrl: './mouvement-update.component.html'
})
export class MouvementUpdateComponent implements OnInit {
  isSaving = false;
  produits: IProduit[] = [];
  typemvtstocks: ITypeMvtStock[] = [];

  editForm = this.fb.group({
    id: [],
    codeMvt: [],
    libelleMvt: [],
    stockEntrant: [],
    stockSortant: [],
    produit: [],
    typeMvtStock: []
  });

  constructor(
    protected mouvementService: MouvementService,
    protected produitService: ProduitService,
    protected typeMvtStockService: TypeMvtStockService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mouvement }) => {
      this.updateForm(mouvement);

      this.produitService.query().subscribe((res: HttpResponse<IProduit[]>) => (this.produits = res.body || []));

      this.typeMvtStockService.query().subscribe((res: HttpResponse<ITypeMvtStock[]>) => (this.typemvtstocks = res.body || []));
    });
  }

  updateForm(mouvement: IMouvement): void {
    this.editForm.patchValue({
      id: mouvement.id,
      codeMvt: mouvement.codeMvt,
      libelleMvt: mouvement.libelleMvt,
      stockEntrant: mouvement.stockEntrant,
      stockSortant: mouvement.stockSortant,
      produit: mouvement.produit,
      typeMvtStock: mouvement.typeMvtStock
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mouvement = this.createFromForm();
    if (mouvement.id !== undefined) {
      this.subscribeToSaveResponse(this.mouvementService.update(mouvement));
    } else {
      this.subscribeToSaveResponse(this.mouvementService.create(mouvement));
    }
  }

  private createFromForm(): IMouvement {
    return {
      ...new Mouvement(),
      id: this.editForm.get(['id'])!.value,
      codeMvt: this.editForm.get(['codeMvt'])!.value,
      libelleMvt: this.editForm.get(['libelleMvt'])!.value,
      stockEntrant: this.editForm.get(['stockEntrant'])!.value,
      stockSortant: this.editForm.get(['stockSortant'])!.value,
      produit: this.editForm.get(['produit'])!.value,
      typeMvtStock: this.editForm.get(['typeMvtStock'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMouvement>>): void {
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
