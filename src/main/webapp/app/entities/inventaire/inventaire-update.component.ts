import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInventaire, Inventaire } from 'app/shared/model/inventaire.model';
import { InventaireService } from './inventaire.service';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit/produit.service';

@Component({
  selector: 'jhi-inventaire-update',
  templateUrl: './inventaire-update.component.html'
})
export class InventaireUpdateComponent implements OnInit {
  isSaving = false;
  produits: IProduit[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    date: [],
    quantiteEntrant: [],
    quantiteInitiale: [],
    quantiteSortant: [],
    nombreSortant: [],
    nombreLivraison: [],
    nombreRetour: [],
    produit: []
  });

  constructor(
    protected inventaireService: InventaireService,
    protected produitService: ProduitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventaire }) => {
      this.updateForm(inventaire);

      this.produitService.query().subscribe((res: HttpResponse<IProduit[]>) => (this.produits = res.body || []));
    });
  }

  updateForm(inventaire: IInventaire): void {
    this.editForm.patchValue({
      id: inventaire.id,
      code: inventaire.code,
      date: inventaire.date,
      quantiteEntrant: inventaire.quantiteEntrant,
      quantiteInitiale: inventaire.quantiteInitiale,
      quantiteSortant: inventaire.quantiteSortant,
      nombreSortant: inventaire.nombreSortant,
      nombreLivraison: inventaire.nombreLivraison,
      nombreRetour: inventaire.nombreRetour,
      produit: inventaire.produit
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inventaire = this.createFromForm();
    if (inventaire.id !== undefined) {
      this.subscribeToSaveResponse(this.inventaireService.update(inventaire));
    } else {
      this.subscribeToSaveResponse(this.inventaireService.create(inventaire));
    }
  }

  private createFromForm(): IInventaire {
    return {
      ...new Inventaire(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      date: this.editForm.get(['date'])!.value,
      quantiteEntrant: this.editForm.get(['quantiteEntrant'])!.value,
      quantiteInitiale: this.editForm.get(['quantiteInitiale'])!.value,
      quantiteSortant: this.editForm.get(['quantiteSortant'])!.value,
      nombreSortant: this.editForm.get(['nombreSortant'])!.value,
      nombreLivraison: this.editForm.get(['nombreLivraison'])!.value,
      nombreRetour: this.editForm.get(['nombreRetour'])!.value,
      produit: this.editForm.get(['produit'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventaire>>): void {
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

  trackById(index: number, item: IProduit): any {
    return item.id;
  }
}
