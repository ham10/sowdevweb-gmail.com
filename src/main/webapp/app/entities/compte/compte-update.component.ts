import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICompte, Compte } from 'app/shared/model/compte.model';
import { CompteService } from './compte.service';
import { ICompteGene } from 'app/shared/model/compte-gene.model';
import { CompteGeneService } from 'app/entities/compte-gene/compte-gene.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';

type SelectableEntity = ICompteGene | IPatient;

@Component({
  selector: 'jhi-compte-update',
  templateUrl: './compte-update.component.html'
})
export class CompteUpdateComponent implements OnInit {
  isSaving = false;
  comptegenes: ICompteGene[] = [];
  patients: IPatient[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    numeroCompte: [],
    libelleCompte: [],
    soldeCompte: [],
    sensCompte: [],
    cumulMouvDebit: [],
    cumulMouvCredit: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    compteGeneral: [],
    patient: []
  });

  constructor(
    protected compteService: CompteService,
    protected compteGeneService: CompteGeneService,
    protected patientService: PatientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ compte }) => {
      this.updateForm(compte);

      this.compteGeneService.query().subscribe((res: HttpResponse<ICompteGene[]>) => (this.comptegenes = res.body || []));

      this.patientService.query().subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body || []));
    });
  }

  updateForm(compte: ICompte): void {
    this.editForm.patchValue({
      id: compte.id,
      numeroCompte: compte.numeroCompte,
      libelleCompte: compte.libelleCompte,
      soldeCompte: compte.soldeCompte,
      sensCompte: compte.sensCompte,
      cumulMouvDebit: compte.cumulMouvDebit,
      cumulMouvCredit: compte.cumulMouvCredit,
      dateCreated: compte.dateCreated,
      dateUpdated: compte.dateUpdated,
      dateDeleted: compte.dateDeleted,
      userCreated: compte.userCreated,
      userUpdated: compte.userUpdated,
      userDeleted: compte.userDeleted,
      compteGeneral: compte.compteGeneral,
      patient: compte.patient
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const compte = this.createFromForm();
    if (compte.id !== undefined) {
      this.subscribeToSaveResponse(this.compteService.update(compte));
    } else {
      this.subscribeToSaveResponse(this.compteService.create(compte));
    }
  }

  private createFromForm(): ICompte {
    return {
      ...new Compte(),
      id: this.editForm.get(['id'])!.value,
      numeroCompte: this.editForm.get(['numeroCompte'])!.value,
      libelleCompte: this.editForm.get(['libelleCompte'])!.value,
      soldeCompte: this.editForm.get(['soldeCompte'])!.value,
      sensCompte: this.editForm.get(['sensCompte'])!.value,
      cumulMouvDebit: this.editForm.get(['cumulMouvDebit'])!.value,
      cumulMouvCredit: this.editForm.get(['cumulMouvCredit'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      compteGeneral: this.editForm.get(['compteGeneral'])!.value,
      patient: this.editForm.get(['patient'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompte>>): void {
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
