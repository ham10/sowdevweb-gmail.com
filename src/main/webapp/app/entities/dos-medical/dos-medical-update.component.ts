import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDosMedical, DosMedical } from 'app/shared/model/dos-medical.model';
import { DosMedicalService } from './dos-medical.service';
import { IAntecedent } from 'app/shared/model/antecedent.model';
import { AntecedentService } from 'app/entities/antecedent/antecedent.service';
import { IFicheMedical } from 'app/shared/model/fiche-medical.model';
import { FicheMedicalService } from 'app/entities/fiche-medical/fiche-medical.service';
import { IServices } from 'app/shared/model/services.model';
import { ServicesService } from 'app/entities/services/services.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';

type SelectableEntity = IAntecedent | IFicheMedical | IServices | IPatient;

@Component({
  selector: 'jhi-dos-medical-update',
  templateUrl: './dos-medical-update.component.html'
})
export class DosMedicalUpdateComponent implements OnInit {
  isSaving = false;
  antecedents: IAntecedent[] = [];
  fichemedicals: IFicheMedical[] = [];
  services: IServices[] = [];
  patients: IPatient[] = [];
  dateCreationDp: any;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    dateCreation: [],
    numeroDossierDosMedical: [],
    niveauDependance: [],
    etatConscience: [],
    etatCutane: [],
    intoleranceMedic: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    antecedents: [],
    ficheMedical: [],
    serv: [],
    patient: []
  });

  constructor(
    protected dosMedicalService: DosMedicalService,
    protected antecedentService: AntecedentService,
    protected ficheMedicalService: FicheMedicalService,
    protected servicesService: ServicesService,
    protected patientService: PatientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dosMedical }) => {
      this.updateForm(dosMedical);

      this.antecedentService.query().subscribe((res: HttpResponse<IAntecedent[]>) => (this.antecedents = res.body || []));

      this.ficheMedicalService.query().subscribe((res: HttpResponse<IFicheMedical[]>) => (this.fichemedicals = res.body || []));

      this.servicesService.query().subscribe((res: HttpResponse<IServices[]>) => (this.services = res.body || []));

      this.patientService.query().subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body || []));
    });
  }

  updateForm(dosMedical: IDosMedical): void {
    this.editForm.patchValue({
      id: dosMedical.id,
      dateCreation: dosMedical.dateCreation,
      numeroDossierDosMedical: dosMedical.numeroDossierDosMedical,
      niveauDependance: dosMedical.niveauDependance,
      etatConscience: dosMedical.etatConscience,
      etatCutane: dosMedical.etatCutane,
      intoleranceMedic: dosMedical.intoleranceMedic,
      dateCreated: dosMedical.dateCreated,
      dateUpdated: dosMedical.dateUpdated,
      userCreated: dosMedical.userCreated,
      userUpdated: dosMedical.userUpdated,
      userDeleted: dosMedical.userDeleted,
      antecedents: dosMedical.antecedents,
      ficheMedical: dosMedical.ficheMedical,
      serv: dosMedical.serv,
      patient: dosMedical.patient
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dosMedical = this.createFromForm();
    if (dosMedical.id !== undefined) {
      this.subscribeToSaveResponse(this.dosMedicalService.update(dosMedical));
    } else {
      this.subscribeToSaveResponse(this.dosMedicalService.create(dosMedical));
    }
  }

  private createFromForm(): IDosMedical {
    return {
      ...new DosMedical(),
      id: this.editForm.get(['id'])!.value,
      dateCreation: this.editForm.get(['dateCreation'])!.value,
      numeroDossierDosMedical: this.editForm.get(['numeroDossierDosMedical'])!.value,
      niveauDependance: this.editForm.get(['niveauDependance'])!.value,
      etatConscience: this.editForm.get(['etatConscience'])!.value,
      etatCutane: this.editForm.get(['etatCutane'])!.value,
      intoleranceMedic: this.editForm.get(['intoleranceMedic'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      antecedents: this.editForm.get(['antecedents'])!.value,
      ficheMedical: this.editForm.get(['ficheMedical'])!.value,
      serv: this.editForm.get(['serv'])!.value,
      patient: this.editForm.get(['patient'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDosMedical>>): void {
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

  getSelected(selectedVals: IAntecedent[], option: IAntecedent): IAntecedent {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
