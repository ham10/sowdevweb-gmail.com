import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILigneCommande, LigneCommande } from 'app/shared/model/ligne-commande.model';
import { LigneCommandeService } from './ligne-commande.service';
import { IProdFournis } from 'app/shared/model/prod-fournis.model';
import { ProdFournisService } from 'app/entities/prod-fournis/prod-fournis.service';
import { IBonDeCommande } from 'app/shared/model/bon-de-commande.model';
import { BonDeCommandeService } from 'app/entities/bon-de-commande/bon-de-commande.service';

type SelectableEntity = IProdFournis | IBonDeCommande;

@Component({
  selector: 'jhi-ligne-commande-update',
  templateUrl: './ligne-commande-update.component.html'
})
export class LigneCommandeUpdateComponent implements OnInit {
  isSaving = false;
  prodfournis: IProdFournis[] = [];
  bondecommandes: IBonDeCommande[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    produit: [],
    quantite: [],
    prixUnitaire: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    produitFournisseur: [],
    bonDeCommande: []
  });

  constructor(
    protected ligneCommandeService: LigneCommandeService,
    protected prodFournisService: ProdFournisService,
    protected bonDeCommandeService: BonDeCommandeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ligneCommande }) => {
      this.updateForm(ligneCommande);

      this.prodFournisService.query().subscribe((res: HttpResponse<IProdFournis[]>) => (this.prodfournis = res.body || []));

      this.bonDeCommandeService.query().subscribe((res: HttpResponse<IBonDeCommande[]>) => (this.bondecommandes = res.body || []));
    });
  }

  updateForm(ligneCommande: ILigneCommande): void {
    this.editForm.patchValue({
      id: ligneCommande.id,
      produit: ligneCommande.produit,
      quantite: ligneCommande.quantite,
      prixUnitaire: ligneCommande.prixUnitaire,
      dateCreated: ligneCommande.dateCreated,
      dateUpdated: ligneCommande.dateUpdated,
      userCreated: ligneCommande.userCreated,
      userUpdated: ligneCommande.userUpdated,
      userDeleted: ligneCommande.userDeleted,
      produitFournisseur: ligneCommande.produitFournisseur,
      bonDeCommande: ligneCommande.bonDeCommande
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ligneCommande = this.createFromForm();
    if (ligneCommande.id !== undefined) {
      this.subscribeToSaveResponse(this.ligneCommandeService.update(ligneCommande));
    } else {
      this.subscribeToSaveResponse(this.ligneCommandeService.create(ligneCommande));
    }
  }

  private createFromForm(): ILigneCommande {
    return {
      ...new LigneCommande(),
      id: this.editForm.get(['id'])!.value,
      produit: this.editForm.get(['produit'])!.value,
      quantite: this.editForm.get(['quantite'])!.value,
      prixUnitaire: this.editForm.get(['prixUnitaire'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      produitFournisseur: this.editForm.get(['produitFournisseur'])!.value,
      bonDeCommande: this.editForm.get(['bonDeCommande'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILigneCommande>>): void {
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
