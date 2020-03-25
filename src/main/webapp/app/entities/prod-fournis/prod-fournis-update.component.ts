import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProdFournis, ProdFournis } from 'app/shared/model/prod-fournis.model';
import { ProdFournisService } from './prod-fournis.service';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit/produit.service';
import { IFournisseur } from 'app/shared/model/fournisseur.model';
import { FournisseurService } from 'app/entities/fournisseur/fournisseur.service';

type SelectableEntity = IProduit | IFournisseur;

@Component({
  selector: 'jhi-prod-fournis-update',
  templateUrl: './prod-fournis-update.component.html'
})
export class ProdFournisUpdateComponent implements OnInit {
  isSaving = false;
  produits: IProduit[] = [];
  fournisseurs: IFournisseur[] = [];

  editForm = this.fb.group({
    id: [],
    stock: [],
    nom: [],
    produit: [],
    fournisseur: []
  });

  constructor(
    protected prodFournisService: ProdFournisService,
    protected produitService: ProduitService,
    protected fournisseurService: FournisseurService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prodFournis }) => {
      this.updateForm(prodFournis);

      this.produitService.query().subscribe((res: HttpResponse<IProduit[]>) => (this.produits = res.body || []));

      this.fournisseurService.query().subscribe((res: HttpResponse<IFournisseur[]>) => (this.fournisseurs = res.body || []));
    });
  }

  updateForm(prodFournis: IProdFournis): void {
    this.editForm.patchValue({
      id: prodFournis.id,
      stock: prodFournis.stock,
      nom: prodFournis.nom,
      produit: prodFournis.produit,
      fournisseur: prodFournis.fournisseur
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const prodFournis = this.createFromForm();
    if (prodFournis.id !== undefined) {
      this.subscribeToSaveResponse(this.prodFournisService.update(prodFournis));
    } else {
      this.subscribeToSaveResponse(this.prodFournisService.create(prodFournis));
    }
  }

  private createFromForm(): IProdFournis {
    return {
      ...new ProdFournis(),
      id: this.editForm.get(['id'])!.value,
      stock: this.editForm.get(['stock'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      produit: this.editForm.get(['produit'])!.value,
      fournisseur: this.editForm.get(['fournisseur'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProdFournis>>): void {
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
