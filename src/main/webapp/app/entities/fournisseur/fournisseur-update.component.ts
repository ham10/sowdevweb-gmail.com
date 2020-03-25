import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFournisseur, Fournisseur } from 'app/shared/model/fournisseur.model';
import { FournisseurService } from './fournisseur.service';

@Component({
  selector: 'jhi-fournisseur-update',
  templateUrl: './fournisseur-update.component.html'
})
export class FournisseurUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    nom: [],
    statut: [],
    raisonSociale: [],
    adresse: [],
    telephone: [],
    ninea: [],
    rc: [],
    ville: [],
    email: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected fournisseurService: FournisseurService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fournisseur }) => {
      this.updateForm(fournisseur);
    });
  }

  updateForm(fournisseur: IFournisseur): void {
    this.editForm.patchValue({
      id: fournisseur.id,
      nom: fournisseur.nom,
      statut: fournisseur.statut,
      raisonSociale: fournisseur.raisonSociale,
      adresse: fournisseur.adresse,
      telephone: fournisseur.telephone,
      ninea: fournisseur.ninea,
      rc: fournisseur.rc,
      ville: fournisseur.ville,
      email: fournisseur.email,
      dateCreated: fournisseur.dateCreated,
      dateUpdated: fournisseur.dateUpdated,
      userCreated: fournisseur.userCreated,
      userUpdated: fournisseur.userUpdated,
      userDeleted: fournisseur.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fournisseur = this.createFromForm();
    if (fournisseur.id !== undefined) {
      this.subscribeToSaveResponse(this.fournisseurService.update(fournisseur));
    } else {
      this.subscribeToSaveResponse(this.fournisseurService.create(fournisseur));
    }
  }

  private createFromForm(): IFournisseur {
    return {
      ...new Fournisseur(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      raisonSociale: this.editForm.get(['raisonSociale'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      ninea: this.editForm.get(['ninea'])!.value,
      rc: this.editForm.get(['rc'])!.value,
      ville: this.editForm.get(['ville'])!.value,
      email: this.editForm.get(['email'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFournisseur>>): void {
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
