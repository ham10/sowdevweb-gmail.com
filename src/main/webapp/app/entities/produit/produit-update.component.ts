import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProduit, Produit } from 'app/shared/model/produit.model';
import { ProduitService } from './produit.service';
import { IEtagere } from 'app/shared/model/etagere.model';
import { EtagereService } from 'app/entities/etagere/etagere.service';
import { ITypeProd } from 'app/shared/model/type-prod.model';
import { TypeProdService } from 'app/entities/type-prod/type-prod.service';

type SelectableEntity = IEtagere | ITypeProd;

@Component({
  selector: 'jhi-produit-update',
  templateUrl: './produit-update.component.html'
})
export class ProduitUpdateComponent implements OnInit {
  isSaving = false;
  etageres: IEtagere[] = [];
  typeprods: ITypeProd[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: [],
    quantiteStock: [],
    stockProvisoire: [],
    tva: [],
    prixVenteUnitaire: [],
    codeBarre: [],
    seuil: [],
    etagere: [],
    typeproduit: []
  });

  constructor(
    protected produitService: ProduitService,
    protected etagereService: EtagereService,
    protected typeProdService: TypeProdService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ produit }) => {
      this.updateForm(produit);

      this.etagereService.query().subscribe((res: HttpResponse<IEtagere[]>) => (this.etageres = res.body || []));

      this.typeProdService.query().subscribe((res: HttpResponse<ITypeProd[]>) => (this.typeprods = res.body || []));
    });
  }

  updateForm(produit: IProduit): void {
    this.editForm.patchValue({
      id: produit.id,
      code: produit.code,
      libelle: produit.libelle,
      quantiteStock: produit.quantiteStock,
      stockProvisoire: produit.stockProvisoire,
      tva: produit.tva,
      prixVenteUnitaire: produit.prixVenteUnitaire,
      codeBarre: produit.codeBarre,
      seuil: produit.seuil,
      etagere: produit.etagere,
      typeproduit: produit.typeproduit
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const produit = this.createFromForm();
    if (produit.id !== undefined) {
      this.subscribeToSaveResponse(this.produitService.update(produit));
    } else {
      this.subscribeToSaveResponse(this.produitService.create(produit));
    }
  }

  private createFromForm(): IProduit {
    return {
      ...new Produit(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      quantiteStock: this.editForm.get(['quantiteStock'])!.value,
      stockProvisoire: this.editForm.get(['stockProvisoire'])!.value,
      tva: this.editForm.get(['tva'])!.value,
      prixVenteUnitaire: this.editForm.get(['prixVenteUnitaire'])!.value,
      codeBarre: this.editForm.get(['codeBarre'])!.value,
      seuil: this.editForm.get(['seuil'])!.value,
      etagere: this.editForm.get(['etagere'])!.value,
      typeproduit: this.editForm.get(['typeproduit'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduit>>): void {
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
