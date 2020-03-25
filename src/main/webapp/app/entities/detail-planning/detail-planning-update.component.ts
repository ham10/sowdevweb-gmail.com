import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IDetailPlanning, DetailPlanning } from 'app/shared/model/detail-planning.model';
import { DetailPlanningService } from './detail-planning.service';
import { IPlanning } from 'app/shared/model/planning.model';
import { PlanningService } from 'app/entities/planning/planning.service';
import { IEtatPlanning } from 'app/shared/model/etat-planning.model';
import { EtatPlanningService } from 'app/entities/etat-planning/etat-planning.service';

type SelectableEntity = IPlanning | IEtatPlanning;

@Component({
  selector: 'jhi-detail-planning-update',
  templateUrl: './detail-planning-update.component.html'
})
export class DetailPlanningUpdateComponent implements OnInit {
  isSaving = false;
  plannings: IPlanning[] = [];
  etatplannings: IEtatPlanning[] = [];

  editForm = this.fb.group({
    id: [],
    titre: [],
    dateDebut: [],
    dateFin: [],
    planning: [],
    etatPlanning: []
  });

  constructor(
    protected detailPlanningService: DetailPlanningService,
    protected planningService: PlanningService,
    protected etatPlanningService: EtatPlanningService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detailPlanning }) => {
      if (!detailPlanning.id) {
        const today = moment().startOf('day');
        detailPlanning.dateDebut = today;
        detailPlanning.dateFin = today;
      }

      this.updateForm(detailPlanning);

      this.planningService.query().subscribe((res: HttpResponse<IPlanning[]>) => (this.plannings = res.body || []));

      this.etatPlanningService.query().subscribe((res: HttpResponse<IEtatPlanning[]>) => (this.etatplannings = res.body || []));
    });
  }

  updateForm(detailPlanning: IDetailPlanning): void {
    this.editForm.patchValue({
      id: detailPlanning.id,
      titre: detailPlanning.titre,
      dateDebut: detailPlanning.dateDebut ? detailPlanning.dateDebut.format(DATE_TIME_FORMAT) : null,
      dateFin: detailPlanning.dateFin ? detailPlanning.dateFin.format(DATE_TIME_FORMAT) : null,
      planning: detailPlanning.planning,
      etatPlanning: detailPlanning.etatPlanning
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const detailPlanning = this.createFromForm();
    if (detailPlanning.id !== undefined) {
      this.subscribeToSaveResponse(this.detailPlanningService.update(detailPlanning));
    } else {
      this.subscribeToSaveResponse(this.detailPlanningService.create(detailPlanning));
    }
  }

  private createFromForm(): IDetailPlanning {
    return {
      ...new DetailPlanning(),
      id: this.editForm.get(['id'])!.value,
      titre: this.editForm.get(['titre'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value ? moment(this.editForm.get(['dateDebut'])!.value, DATE_TIME_FORMAT) : undefined,
      dateFin: this.editForm.get(['dateFin'])!.value ? moment(this.editForm.get(['dateFin'])!.value, DATE_TIME_FORMAT) : undefined,
      planning: this.editForm.get(['planning'])!.value,
      etatPlanning: this.editForm.get(['etatPlanning'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetailPlanning>>): void {
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
