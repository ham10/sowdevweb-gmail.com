import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDepartement, Departement } from 'app/shared/model/departement.model';
import { DepartementService } from './departement.service';
import { IRegion } from 'app/shared/model/region.model';
import { RegionService } from 'app/entities/region/region.service';

@Component({
  selector: 'jhi-departement-update',
  templateUrl: './departement-update.component.html'
})
export class DepartementUpdateComponent implements OnInit {
  isSaving = false;
  regions: IRegion[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeDepartement: [],
    libelleDepartement: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    region: []
  });

  constructor(
    protected departementService: DepartementService,
    protected regionService: RegionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ departement }) => {
      this.updateForm(departement);

      this.regionService.query().subscribe((res: HttpResponse<IRegion[]>) => (this.regions = res.body || []));
    });
  }

  updateForm(departement: IDepartement): void {
    this.editForm.patchValue({
      id: departement.id,
      codeDepartement: departement.codeDepartement,
      libelleDepartement: departement.libelleDepartement,
      dateCreated: departement.dateCreated,
      dateUpdated: departement.dateUpdated,
      userCreated: departement.userCreated,
      userUpdated: departement.userUpdated,
      userDeleted: departement.userDeleted,
      region: departement.region
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const departement = this.createFromForm();
    if (departement.id !== undefined) {
      this.subscribeToSaveResponse(this.departementService.update(departement));
    } else {
      this.subscribeToSaveResponse(this.departementService.create(departement));
    }
  }

  private createFromForm(): IDepartement {
    return {
      ...new Departement(),
      id: this.editForm.get(['id'])!.value,
      codeDepartement: this.editForm.get(['codeDepartement'])!.value,
      libelleDepartement: this.editForm.get(['libelleDepartement'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      region: this.editForm.get(['region'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepartement>>): void {
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

  trackById(index: number, item: IRegion): any {
    return item.id;
  }
}
