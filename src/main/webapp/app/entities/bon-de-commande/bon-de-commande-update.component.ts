import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IBonDeCommande, BonDeCommande } from 'app/shared/model/bon-de-commande.model';
import { BonDeCommandeService } from './bon-de-commande.service';
import { IBonLivraison } from 'app/shared/model/bon-livraison.model';
import { BonLivraisonService } from 'app/entities/bon-livraison/bon-livraison.service';
import { IServices } from 'app/shared/model/services.model';
import { ServicesService } from 'app/entities/services/services.service';
import { IEtatBonCom } from 'app/shared/model/etat-bon-com.model';
import { EtatBonComService } from 'app/entities/etat-bon-com/etat-bon-com.service';
import { ITypeBonCom } from 'app/shared/model/type-bon-com.model';
import { TypeBonComService } from 'app/entities/type-bon-com/type-bon-com.service';

type SelectableEntity = IBonLivraison | IServices | IEtatBonCom | ITypeBonCom;

@Component({
  selector: 'jhi-bon-de-commande-update',
  templateUrl: './bon-de-commande-update.component.html'
})
export class BonDeCommandeUpdateComponent implements OnInit {
  isSaving = false;
  bonlivraisons: IBonLivraison[] = [];
  services: IServices[] = [];
  etatboncoms: IEtatBonCom[] = [];
  typeboncoms: ITypeBonCom[] = [];
  dateCommDp: any;
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    numero: [],
    libelle: [],
    prixTotal: [],
    dateComm: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    bonLivraison: [],
    serv: [],
    etatBonCommande: [],
    typeBonDeCommande: []
  });

  constructor(
    protected bonDeCommandeService: BonDeCommandeService,
    protected bonLivraisonService: BonLivraisonService,
    protected servicesService: ServicesService,
    protected etatBonComService: EtatBonComService,
    protected typeBonComService: TypeBonComService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bonDeCommande }) => {
      this.updateForm(bonDeCommande);

      this.bonLivraisonService
        .query({ filter: 'bondecommande-is-null' })
        .pipe(
          map((res: HttpResponse<IBonLivraison[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IBonLivraison[]) => {
          if (!bonDeCommande.bonLivraison || !bonDeCommande.bonLivraison.id) {
            this.bonlivraisons = resBody;
          } else {
            this.bonLivraisonService
              .find(bonDeCommande.bonLivraison.id)
              .pipe(
                map((subRes: HttpResponse<IBonLivraison>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IBonLivraison[]) => (this.bonlivraisons = concatRes));
          }
        });

      this.servicesService.query().subscribe((res: HttpResponse<IServices[]>) => (this.services = res.body || []));

      this.etatBonComService.query().subscribe((res: HttpResponse<IEtatBonCom[]>) => (this.etatboncoms = res.body || []));

      this.typeBonComService.query().subscribe((res: HttpResponse<ITypeBonCom[]>) => (this.typeboncoms = res.body || []));
    });
  }

  updateForm(bonDeCommande: IBonDeCommande): void {
    this.editForm.patchValue({
      id: bonDeCommande.id,
      numero: bonDeCommande.numero,
      libelle: bonDeCommande.libelle,
      prixTotal: bonDeCommande.prixTotal,
      dateComm: bonDeCommande.dateComm,
      dateCreated: bonDeCommande.dateCreated,
      dateUpdated: bonDeCommande.dateUpdated,
      dateDeleted: bonDeCommande.dateDeleted,
      userCreated: bonDeCommande.userCreated,
      userUpdated: bonDeCommande.userUpdated,
      userDeleted: bonDeCommande.userDeleted,
      bonLivraison: bonDeCommande.bonLivraison,
      serv: bonDeCommande.serv,
      etatBonCommande: bonDeCommande.etatBonCommande,
      typeBonDeCommande: bonDeCommande.typeBonDeCommande
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bonDeCommande = this.createFromForm();
    if (bonDeCommande.id !== undefined) {
      this.subscribeToSaveResponse(this.bonDeCommandeService.update(bonDeCommande));
    } else {
      this.subscribeToSaveResponse(this.bonDeCommandeService.create(bonDeCommande));
    }
  }

  private createFromForm(): IBonDeCommande {
    return {
      ...new BonDeCommande(),
      id: this.editForm.get(['id'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      prixTotal: this.editForm.get(['prixTotal'])!.value,
      dateComm: this.editForm.get(['dateComm'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      bonLivraison: this.editForm.get(['bonLivraison'])!.value,
      serv: this.editForm.get(['serv'])!.value,
      etatBonCommande: this.editForm.get(['etatBonCommande'])!.value,
      typeBonDeCommande: this.editForm.get(['typeBonDeCommande'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBonDeCommande>>): void {
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
