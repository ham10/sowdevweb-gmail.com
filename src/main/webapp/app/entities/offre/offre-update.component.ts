import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IOffre, Offre } from 'app/shared/model/offre.model';
import { OffreService } from './offre.service';
import { IBonDeCommande } from 'app/shared/model/bon-de-commande.model';
import { BonDeCommandeService } from 'app/entities/bon-de-commande/bon-de-commande.service';
import { IFournisseur } from 'app/shared/model/fournisseur.model';
import { FournisseurService } from 'app/entities/fournisseur/fournisseur.service';

type SelectableEntity = IBonDeCommande | IFournisseur;

@Component({
  selector: 'jhi-offre-update',
  templateUrl: './offre-update.component.html'
})
export class OffreUpdateComponent implements OnInit {
  isSaving = false;
  bondecommandes: IBonDeCommande[] = [];
  fournisseurs: IFournisseur[] = [];
  libelleDp: any;
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
    numMarche: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    bonDeCommande: [],
    fournisseur: []
  });

  constructor(
    protected offreService: OffreService,
    protected bonDeCommandeService: BonDeCommandeService,
    protected fournisseurService: FournisseurService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offre }) => {
      this.updateForm(offre);

      this.bonDeCommandeService
        .query({ filter: 'offre-is-null' })
        .pipe(
          map((res: HttpResponse<IBonDeCommande[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IBonDeCommande[]) => {
          if (!offre.bonDeCommande || !offre.bonDeCommande.id) {
            this.bondecommandes = resBody;
          } else {
            this.bonDeCommandeService
              .find(offre.bonDeCommande.id)
              .pipe(
                map((subRes: HttpResponse<IBonDeCommande>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IBonDeCommande[]) => (this.bondecommandes = concatRes));
          }
        });

      this.fournisseurService.query().subscribe((res: HttpResponse<IFournisseur[]>) => (this.fournisseurs = res.body || []));
    });
  }

  updateForm(offre: IOffre): void {
    this.editForm.patchValue({
      id: offre.id,
      libelle: offre.libelle,
      date: offre.date,
      montant: offre.montant,
      taxe: offre.taxe,
      numMarche: offre.numMarche,
      dateCreated: offre.dateCreated,
      dateUpdated: offre.dateUpdated,
      dateDeleted: offre.dateDeleted,
      userCreated: offre.userCreated,
      userUpdated: offre.userUpdated,
      userDeleted: offre.userDeleted,
      bonDeCommande: offre.bonDeCommande,
      fournisseur: offre.fournisseur
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const offre = this.createFromForm();
    if (offre.id !== undefined) {
      this.subscribeToSaveResponse(this.offreService.update(offre));
    } else {
      this.subscribeToSaveResponse(this.offreService.create(offre));
    }
  }

  private createFromForm(): IOffre {
    return {
      ...new Offre(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      date: this.editForm.get(['date'])!.value,
      montant: this.editForm.get(['montant'])!.value,
      taxe: this.editForm.get(['taxe'])!.value,
      numMarche: this.editForm.get(['numMarche'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      bonDeCommande: this.editForm.get(['bonDeCommande'])!.value,
      fournisseur: this.editForm.get(['fournisseur'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOffre>>): void {
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
