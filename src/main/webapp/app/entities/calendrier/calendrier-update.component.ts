import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICalendrier, Calendrier } from 'app/shared/model/calendrier.model';
import { CalendrierService } from './calendrier.service';

@Component({
  selector: 'jhi-calendrier-update',
  templateUrl: './calendrier-update.component.html'
})
export class CalendrierUpdateComponent implements OnInit {
  isSaving = false;
  heureDebutDp: any;
  heureFinDp: any;
  dateDebutDp: any;
  dateFinDp: any;
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;
  userCreatedDp: any;

  editForm = this.fb.group({
    id: [],
    libelleCalendrier: [],
    heureDebut: [],
    heureFin: [],
    dateDebut: [],
    dateFin: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected calendrierService: CalendrierService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ calendrier }) => {
      this.updateForm(calendrier);
    });
  }

  updateForm(calendrier: ICalendrier): void {
    this.editForm.patchValue({
      id: calendrier.id,
      libelleCalendrier: calendrier.libelleCalendrier,
      heureDebut: calendrier.heureDebut,
      heureFin: calendrier.heureFin,
      dateDebut: calendrier.dateDebut,
      dateFin: calendrier.dateFin,
      dateCreated: calendrier.dateCreated,
      dateUpdated: calendrier.dateUpdated,
      dateDeleted: calendrier.dateDeleted,
      userCreated: calendrier.userCreated,
      userUpdated: calendrier.userUpdated,
      userDeleted: calendrier.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const calendrier = this.createFromForm();
    if (calendrier.id !== undefined) {
      this.subscribeToSaveResponse(this.calendrierService.update(calendrier));
    } else {
      this.subscribeToSaveResponse(this.calendrierService.create(calendrier));
    }
  }

  private createFromForm(): ICalendrier {
    return {
      ...new Calendrier(),
      id: this.editForm.get(['id'])!.value,
      libelleCalendrier: this.editForm.get(['libelleCalendrier'])!.value,
      heureDebut: this.editForm.get(['heureDebut'])!.value,
      heureFin: this.editForm.get(['heureFin'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value,
      dateFin: this.editForm.get(['dateFin'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICalendrier>>): void {
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
