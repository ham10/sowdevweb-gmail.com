import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IHospitalisation, Hospitalisation } from 'app/shared/model/hospitalisation.model';
import { HospitalisationService } from './hospitalisation.service';
import { ILit } from 'app/shared/model/lit.model';
import { LitService } from 'app/entities/lit/lit.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';

type SelectableEntity = ILit | IPatient;

@Component({
  selector: 'jhi-hospitalisation-update',
  templateUrl: './hospitalisation-update.component.html'
})
export class HospitalisationUpdateComponent implements OnInit {
  isSaving = false;
  lits: ILit[] = [];
  patients: IPatient[] = [];
  dateEntreDp: any;
  dateSortieDp: any;
  dateOperationDp: any;
  dateReveilDp: any;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    dateEntre: [],
    dateSortie: [],
    observation: [],
    modeSortie: [],
    objetPatient: [],
    dateAccouchement: [],
    dateOperation: [],
    dateReveil: [],
    poidsBebe: [],
    tailleBebe: [],
    poidsPlacenta: [],
    genreBebe: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    lit: [],
    patient: []
  });

  constructor(
    protected hospitalisationService: HospitalisationService,
    protected litService: LitService,
    protected patientService: PatientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hospitalisation }) => {
      if (!hospitalisation.id) {
        const today = moment().startOf('day');
        hospitalisation.dateAccouchement = today;
      }

      this.updateForm(hospitalisation);

      this.litService
        .query({ filter: 'hospitalisation-is-null' })
        .pipe(
          map((res: HttpResponse<ILit[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ILit[]) => {
          if (!hospitalisation.lit || !hospitalisation.lit.id) {
            this.lits = resBody;
          } else {
            this.litService
              .find(hospitalisation.lit.id)
              .pipe(
                map((subRes: HttpResponse<ILit>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ILit[]) => (this.lits = concatRes));
          }
        });

      this.patientService.query().subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body || []));
    });
  }

  updateForm(hospitalisation: IHospitalisation): void {
    this.editForm.patchValue({
      id: hospitalisation.id,
      dateEntre: hospitalisation.dateEntre,
      dateSortie: hospitalisation.dateSortie,
      observation: hospitalisation.observation,
      modeSortie: hospitalisation.modeSortie,
      objetPatient: hospitalisation.objetPatient,
      dateAccouchement: hospitalisation.dateAccouchement ? hospitalisation.dateAccouchement.format(DATE_TIME_FORMAT) : null,
      dateOperation: hospitalisation.dateOperation,
      dateReveil: hospitalisation.dateReveil,
      poidsBebe: hospitalisation.poidsBebe,
      tailleBebe: hospitalisation.tailleBebe,
      poidsPlacenta: hospitalisation.poidsPlacenta,
      genreBebe: hospitalisation.genreBebe,
      dateCreated: hospitalisation.dateCreated,
      dateUpdated: hospitalisation.dateUpdated,
      userCreated: hospitalisation.userCreated,
      userUpdated: hospitalisation.userUpdated,
      userDeleted: hospitalisation.userDeleted,
      lit: hospitalisation.lit,
      patient: hospitalisation.patient
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hospitalisation = this.createFromForm();
    if (hospitalisation.id !== undefined) {
      this.subscribeToSaveResponse(this.hospitalisationService.update(hospitalisation));
    } else {
      this.subscribeToSaveResponse(this.hospitalisationService.create(hospitalisation));
    }
  }

  private createFromForm(): IHospitalisation {
    return {
      ...new Hospitalisation(),
      id: this.editForm.get(['id'])!.value,
      dateEntre: this.editForm.get(['dateEntre'])!.value,
      dateSortie: this.editForm.get(['dateSortie'])!.value,
      observation: this.editForm.get(['observation'])!.value,
      modeSortie: this.editForm.get(['modeSortie'])!.value,
      objetPatient: this.editForm.get(['objetPatient'])!.value,
      dateAccouchement: this.editForm.get(['dateAccouchement'])!.value
        ? moment(this.editForm.get(['dateAccouchement'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dateOperation: this.editForm.get(['dateOperation'])!.value,
      dateReveil: this.editForm.get(['dateReveil'])!.value,
      poidsBebe: this.editForm.get(['poidsBebe'])!.value,
      tailleBebe: this.editForm.get(['tailleBebe'])!.value,
      poidsPlacenta: this.editForm.get(['poidsPlacenta'])!.value,
      genreBebe: this.editForm.get(['genreBebe'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      lit: this.editForm.get(['lit'])!.value,
      patient: this.editForm.get(['patient'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHospitalisation>>): void {
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
