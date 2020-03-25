import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBonLivraison, BonLivraison } from 'app/shared/model/bon-livraison.model';
import { BonLivraisonService } from './bon-livraison.service';

@Component({
  selector: 'jhi-bon-livraison-update',
  templateUrl: './bon-livraison-update.component.html'
})
export class BonLivraisonUpdateComponent implements OnInit {
  isSaving = false;
  dateBonLivraisonDp: any;
  dateCertifDp: any;
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    designationBonLivraison: [],
    prixTotalBonLivraison: [],
    dateBonLivraison: [],
    userCertified: [],
    dateCertif: [],
    numCertif: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected bonLivraisonService: BonLivraisonService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bonLivraison }) => {
      this.updateForm(bonLivraison);
    });
  }

  updateForm(bonLivraison: IBonLivraison): void {
    this.editForm.patchValue({
      id: bonLivraison.id,
      designationBonLivraison: bonLivraison.designationBonLivraison,
      prixTotalBonLivraison: bonLivraison.prixTotalBonLivraison,
      dateBonLivraison: bonLivraison.dateBonLivraison,
      userCertified: bonLivraison.userCertified,
      dateCertif: bonLivraison.dateCertif,
      numCertif: bonLivraison.numCertif,
      dateCreated: bonLivraison.dateCreated,
      dateUpdated: bonLivraison.dateUpdated,
      dateDeleted: bonLivraison.dateDeleted,
      userCreated: bonLivraison.userCreated,
      userUpdated: bonLivraison.userUpdated,
      userDeleted: bonLivraison.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bonLivraison = this.createFromForm();
    if (bonLivraison.id !== undefined) {
      this.subscribeToSaveResponse(this.bonLivraisonService.update(bonLivraison));
    } else {
      this.subscribeToSaveResponse(this.bonLivraisonService.create(bonLivraison));
    }
  }

  private createFromForm(): IBonLivraison {
    return {
      ...new BonLivraison(),
      id: this.editForm.get(['id'])!.value,
      designationBonLivraison: this.editForm.get(['designationBonLivraison'])!.value,
      prixTotalBonLivraison: this.editForm.get(['prixTotalBonLivraison'])!.value,
      dateBonLivraison: this.editForm.get(['dateBonLivraison'])!.value,
      userCertified: this.editForm.get(['userCertified'])!.value,
      dateCertif: this.editForm.get(['dateCertif'])!.value,
      numCertif: this.editForm.get(['numCertif'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBonLivraison>>): void {
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
}
