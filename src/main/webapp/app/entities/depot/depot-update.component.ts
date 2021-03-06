import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDepot, Depot } from 'app/shared/model/depot.model';
import { DepotService } from './depot.service';

@Component({
  selector: 'jhi-depot-update',
  templateUrl: './depot-update.component.html'
})
export class DepotUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    codeDepot: [],
    libelleDepot: []
  });

  constructor(protected depotService: DepotService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ depot }) => {
      this.updateForm(depot);
    });
  }

  updateForm(depot: IDepot): void {
    this.editForm.patchValue({
      id: depot.id,
      codeDepot: depot.codeDepot,
      libelleDepot: depot.libelleDepot
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const depot = this.createFromForm();
    if (depot.id !== undefined) {
      this.subscribeToSaveResponse(this.depotService.update(depot));
    } else {
      this.subscribeToSaveResponse(this.depotService.create(depot));
    }
  }

  private createFromForm(): IDepot {
    return {
      ...new Depot(),
      id: this.editForm.get(['id'])!.value,
      codeDepot: this.editForm.get(['codeDepot'])!.value,
      libelleDepot: this.editForm.get(['libelleDepot'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepot>>): void {
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
