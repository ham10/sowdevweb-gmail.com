import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVaccin, Vaccin } from 'app/shared/model/vaccin.model';
import { VaccinService } from './vaccin.service';
import { IFicheMedical } from 'app/shared/model/fiche-medical.model';
import { FicheMedicalService } from 'app/entities/fiche-medical/fiche-medical.service';

@Component({
  selector: 'jhi-vaccin-update',
  templateUrl: './vaccin-update.component.html'
})
export class VaccinUpdateComponent implements OnInit {
  isSaving = false;
  fichemedicals: IFicheMedical[] = [];
  dateDp: any;
  dateRenouvDp: any;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    description: [],
    date: [],
    dateRenouv: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    ficheMedical: []
  });

  constructor(
    protected vaccinService: VaccinService,
    protected ficheMedicalService: FicheMedicalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vaccin }) => {
      this.updateForm(vaccin);

      this.ficheMedicalService.query().subscribe((res: HttpResponse<IFicheMedical[]>) => (this.fichemedicals = res.body || []));
    });
  }

  updateForm(vaccin: IVaccin): void {
    this.editForm.patchValue({
      id: vaccin.id,
      description: vaccin.description,
      date: vaccin.date,
      dateRenouv: vaccin.dateRenouv,
      dateCreated: vaccin.dateCreated,
      dateUpdated: vaccin.dateUpdated,
      userCreated: vaccin.userCreated,
      userUpdated: vaccin.userUpdated,
      userDeleted: vaccin.userDeleted,
      ficheMedical: vaccin.ficheMedical
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vaccin = this.createFromForm();
    if (vaccin.id !== undefined) {
      this.subscribeToSaveResponse(this.vaccinService.update(vaccin));
    } else {
      this.subscribeToSaveResponse(this.vaccinService.create(vaccin));
    }
  }

  private createFromForm(): IVaccin {
    return {
      ...new Vaccin(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      date: this.editForm.get(['date'])!.value,
      dateRenouv: this.editForm.get(['dateRenouv'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      ficheMedical: this.editForm.get(['ficheMedical'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVaccin>>): void {
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

  trackById(index: number, item: IFicheMedical): any {
    return item.id;
  }
}
