import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IHoraireCon, HoraireCon } from 'app/shared/model/horaire-con.model';
import { HoraireConService } from './horaire-con.service';
import { IJour } from 'app/shared/model/jour.model';
import { JourService } from 'app/entities/jour/jour.service';

@Component({
  selector: 'jhi-horaire-con-update',
  templateUrl: './horaire-con-update.component.html'
})
export class HoraireConUpdateComponent implements OnInit {
  isSaving = false;
  jours: IJour[] = [];
  heureDebutHCDp: any;
  heureFinHCDp: any;
  dateDebutHCDp: any;
  dateFinHCDp: any;
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    heureDebutHC: [],
    heureFinHC: [],
    dateDebutHC: [],
    dateFinHC: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    jour: []
  });

  constructor(
    protected horaireConService: HoraireConService,
    protected jourService: JourService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ horaireCon }) => {
      this.updateForm(horaireCon);

      this.jourService.query().subscribe((res: HttpResponse<IJour[]>) => (this.jours = res.body || []));
    });
  }

  updateForm(horaireCon: IHoraireCon): void {
    this.editForm.patchValue({
      id: horaireCon.id,
      heureDebutHC: horaireCon.heureDebutHC,
      heureFinHC: horaireCon.heureFinHC,
      dateDebutHC: horaireCon.dateDebutHC,
      dateFinHC: horaireCon.dateFinHC,
      dateCreated: horaireCon.dateCreated,
      dateUpdated: horaireCon.dateUpdated,
      dateDeleted: horaireCon.dateDeleted,
      userCreated: horaireCon.userCreated,
      userUpdated: horaireCon.userUpdated,
      userDeleted: horaireCon.userDeleted,
      jour: horaireCon.jour
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const horaireCon = this.createFromForm();
    if (horaireCon.id !== undefined) {
      this.subscribeToSaveResponse(this.horaireConService.update(horaireCon));
    } else {
      this.subscribeToSaveResponse(this.horaireConService.create(horaireCon));
    }
  }

  private createFromForm(): IHoraireCon {
    return {
      ...new HoraireCon(),
      id: this.editForm.get(['id'])!.value,
      heureDebutHC: this.editForm.get(['heureDebutHC'])!.value,
      heureFinHC: this.editForm.get(['heureFinHC'])!.value,
      dateDebutHC: this.editForm.get(['dateDebutHC'])!.value,
      dateFinHC: this.editForm.get(['dateFinHC'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      jour: this.editForm.get(['jour'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHoraireCon>>): void {
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

  trackById(index: number, item: IJour): any {
    return item.id;
  }
}
