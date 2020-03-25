import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IRDV, RDV } from 'app/shared/model/rdv.model';
import { RDVService } from './rdv.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';
import { IPlanning } from 'app/shared/model/planning.model';
import { PlanningService } from 'app/entities/planning/planning.service';
import { IEtatRdv } from 'app/shared/model/etat-rdv.model';
import { EtatRdvService } from 'app/entities/etat-rdv/etat-rdv.service';

type SelectableEntity = IPatient | IPlanning | IEtatRdv;

@Component({
  selector: 'jhi-rdv-update',
  templateUrl: './rdv-update.component.html'
})
export class RDVUpdateComponent implements OnInit {
  isSaving = false;
  patients: IPatient[] = [];
  plannings: IPlanning[] = [];
  etatrdvs: IEtatRdv[] = [];
  patient: IPatient | null = null;
  dateRdvDp: any;
  searchForm = this.fb.group({
    nameSearch: ['', Validators.required]
  });
  editForm = this.fb.group({
    id: [],
    numRdv: [],
    dateRdv: [],
    heureRdv: [],
    patient: [],
    planning: [],
    etatRDV: []
  });

  constructor(
    protected rDVService: RDVService,
    protected patientService: PatientService,
    protected planningService: PlanningService,
    protected etatRdvService: EtatRdvService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rDV }) => {
      if (!rDV.id) {
        const today = moment().startOf('day');
        rDV.heureRdv = today;
      }

      this.updateForm(rDV);

      this.patientService.query().subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body || []));

      this.planningService.query().subscribe((res: HttpResponse<IPlanning[]>) => (this.plannings = res.body || []));

      this.etatRdvService.query().subscribe((res: HttpResponse<IEtatRdv[]>) => (this.etatrdvs = res.body || []));
    });
  }

  // rechercher un patient
  // searchPatient(): void{
  //   if(this.searchForm.invalid){
  //     return ;
  //   }else {
  //     const codePatient = this.searchForm.value.nameSearch;
  //     this.patientService.findByCode(codePatient).subscribe((res: HttpResponse<IPatient>) => this.patient = res.body);
  //   }
  //
  // }
  updateForm(rDV: IRDV): void {
    this.editForm.patchValue({
      id: rDV.id,
      numRdv: rDV.numRdv,
      dateRdv: rDV.dateRdv,
      heureRdv: rDV.heureRdv ? rDV.heureRdv.format(DATE_TIME_FORMAT) : null,
      patient: rDV.patient,
      planning: rDV.planning,
      etatRDV: rDV.etatRDV
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rDV = this.createFromForm();
    if (rDV.id !== undefined) {
      this.subscribeToSaveResponse(this.rDVService.update(rDV));
    } else {
      this.subscribeToSaveResponse(this.rDVService.create(rDV));
    }
  }

  private createFromForm(): IRDV {
    return {
      ...new RDV(),
      id: this.editForm.get(['id'])!.value,
      numRdv: '',
      dateRdv: this.editForm.get(['dateRdv'])!.value,
      heureRdv: this.editForm.get(['heureRdv'])!.value ? moment(this.editForm.get(['heureRdv'])!.value, DATE_TIME_FORMAT) : undefined,
      patient: this.editForm.get(['patient'])!.value,
      planning: this.editForm.get(['planning'])!.value,
      etatRDV: this.editForm.get(['etatRDV'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRDV>>): void {
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
