import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILigneLivraison, LigneLivraison } from 'app/shared/model/ligne-livraison.model';
import { LigneLivraisonService } from './ligne-livraison.service';
import { IProdFournis } from 'app/shared/model/prod-fournis.model';
import { ProdFournisService } from 'app/entities/prod-fournis/prod-fournis.service';
import { IBonLivraison } from 'app/shared/model/bon-livraison.model';
import { BonLivraisonService } from 'app/entities/bon-livraison/bon-livraison.service';

type SelectableEntity = IProdFournis | IBonLivraison;

@Component({
  selector: 'jhi-ligne-livraison-update',
  templateUrl: './ligne-livraison-update.component.html'
})
export class LigneLivraisonUpdateComponent implements OnInit {
  isSaving = false;
  prodfournis: IProdFournis[] = [];
  bonlivraisons: IBonLivraison[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    designation: [],
    quantite: [],
    prixUnitaire: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    produitFournisseur: [],
    bonDeLivraison: []
  });

  constructor(
    protected ligneLivraisonService: LigneLivraisonService,
    protected prodFournisService: ProdFournisService,
    protected bonLivraisonService: BonLivraisonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ligneLivraison }) => {
      this.updateForm(ligneLivraison);

      this.prodFournisService.query().subscribe((res: HttpResponse<IProdFournis[]>) => (this.prodfournis = res.body || []));

      this.bonLivraisonService.query().subscribe((res: HttpResponse<IBonLivraison[]>) => (this.bonlivraisons = res.body || []));
    });
  }

  updateForm(ligneLivraison: ILigneLivraison): void {
    this.editForm.patchValue({
      id: ligneLivraison.id,
      designation: ligneLivraison.designation,
      quantite: ligneLivraison.quantite,
      prixUnitaire: ligneLivraison.prixUnitaire,
      dateCreated: ligneLivraison.dateCreated,
      dateUpdated: ligneLivraison.dateUpdated,
      dateDeleted: ligneLivraison.dateDeleted,
      userCreated: ligneLivraison.userCreated,
      userUpdated: ligneLivraison.userUpdated,
      userDeleted: ligneLivraison.userDeleted,
      produitFournisseur: ligneLivraison.produitFournisseur,
      bonDeLivraison: ligneLivraison.bonDeLivraison
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ligneLivraison = this.createFromForm();
    if (ligneLivraison.id !== undefined) {
      this.subscribeToSaveResponse(this.ligneLivraisonService.update(ligneLivraison));
    } else {
      this.subscribeToSaveResponse(this.ligneLivraisonService.create(ligneLivraison));
    }
  }

  private createFromForm(): ILigneLivraison {
    return {
      ...new LigneLivraison(),
      id: this.editForm.get(['id'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      quantite: this.editForm.get(['quantite'])!.value,
      prixUnitaire: this.editForm.get(['prixUnitaire'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      produitFournisseur: this.editForm.get(['produitFournisseur'])!.value,
      bonDeLivraison: this.editForm.get(['bonDeLivraison'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILigneLivraison>>): void {
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
