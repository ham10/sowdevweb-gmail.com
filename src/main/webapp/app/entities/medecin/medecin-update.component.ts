import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMedecin, Medecin } from 'app/shared/model/medecin.model';
import { MedecinService } from './medecin.service';
import { IPatient } from 'app/shared/model/patient.model';

@Component({
  selector: 'jhi-medecin-update',
  templateUrl: './medecin-update.component.html'
})
export class MedecinUpdateComponent implements OnInit {
  isSaving = false;
  dateNaissanceDp: any;
  dateEmbaucheDp: any;
  dateValiditeDp: any;
  dateCreatedDp: any;
  dateUpdatedDp: any;
  medecin: IMedecin | null = null;
  dateRdvDp: any;
  searchForm = this.fb.group({
    nameSearch: ['', Validators.required]
  });
  editForm = this.fb.group({
    id: [],
    nomMedecin: [],
    prenom: [],
    adresse: [],
    email: [],
    dateNaissance: [],
    genreMedecin: [],
    nationalite: [],
    telephone: [],
    anciennete: [],
    numeroPiece: [],
    gradeMedecin: [],
    specialite: [],
    dateEmbauche: [],
    dateValidite: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected medecinService: MedecinService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medecin }) => {
      this.updateForm(medecin);
    });
  }
  searchMedecin(): void {
    if (this.searchForm.invalid) {
      return;
    } else {
      const numpieceMedecin = this.searchForm.value.nameSearch;
      this.medecinService.findByNumPiece(numpieceMedecin).subscribe((res: HttpResponse<IMedecin>) => (this.medecin = res.body));
    }
  }
  updateForm(medecin: IMedecin): void {
    this.editForm.patchValue({
      id: medecin.id,
      nomMedecin: medecin.nomMedecin,
      prenom: medecin.prenom,
      adresse: medecin.adresse,
      email: medecin.email,
      dateNaissance: medecin.dateNaissance,
      genreMedecin: medecin.genreMedecin,
      nationalite: medecin.nationalite,
      telephone: medecin.telephone,
      anciennete: medecin.anciennete,
      numeroPiece: medecin.numeroPiece,
      gradeMedecin: medecin.gradeMedecin,
      specialite: medecin.specialite,
      dateEmbauche: medecin.dateEmbauche,
      dateValidite: medecin.dateValidite,
      dateCreated: medecin.dateCreated,
      dateUpdated: medecin.dateUpdated,
      userCreated: medecin.userCreated,
      userUpdated: medecin.userUpdated,
      userDeleted: medecin.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const medecin = this.createFromForm();
    if (medecin.id !== undefined) {
      this.subscribeToSaveResponse(this.medecinService.update(medecin));
    } else {
      this.subscribeToSaveResponse(this.medecinService.create(medecin));
    }
  }

  private createFromForm(): IMedecin {
    return {
      ...new Medecin(),
      id: this.editForm.get(['id'])!.value,
      nomMedecin: this.editForm.get(['nomMedecin'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      email: this.editForm.get(['email'])!.value,
      dateNaissance: this.editForm.get(['dateNaissance'])!.value,
      genreMedecin: this.editForm.get(['genreMedecin'])!.value,
      nationalite: this.editForm.get(['nationalite'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      anciennete: this.editForm.get(['anciennete'])!.value,
      numeroPiece: this.editForm.get(['numeroPiece'])!.value,
      gradeMedecin: this.editForm.get(['gradeMedecin'])!.value,
      specialite: this.editForm.get(['specialite'])!.value,
      dateEmbauche: this.editForm.get(['dateEmbauche'])!.value,
      dateValidite: this.editForm.get(['dateValidite'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedecin>>): void {
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
