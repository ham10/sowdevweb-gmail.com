import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDeptServices, DeptServices } from 'app/shared/model/dept-services.model';
import { DeptServicesService } from './dept-services.service';

@Component({
  selector: 'jhi-dept-services-update',
  templateUrl: './dept-services-update.component.html'
})
export class DeptServicesUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeDeptSrv: [],
    libelleDeptSrv: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected deptServicesService: DeptServicesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deptServices }) => {
      this.updateForm(deptServices);
    });
  }

  updateForm(deptServices: IDeptServices): void {
    this.editForm.patchValue({
      id: deptServices.id,
      codeDeptSrv: deptServices.codeDeptSrv,
      libelleDeptSrv: deptServices.libelleDeptSrv,
      dateCreated: deptServices.dateCreated,
      dateUpdated: deptServices.dateUpdated,
      userCreated: deptServices.userCreated,
      userUpdated: deptServices.userUpdated,
      userDeleted: deptServices.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deptServices = this.createFromForm();
    if (deptServices.id !== undefined) {
      this.subscribeToSaveResponse(this.deptServicesService.update(deptServices));
    } else {
      this.subscribeToSaveResponse(this.deptServicesService.create(deptServices));
    }
  }

  private createFromForm(): IDeptServices {
    return {
      ...new DeptServices(),
      id: this.editForm.get(['id'])!.value,
      codeDeptSrv: this.editForm.get(['codeDeptSrv'])!.value,
      libelleDeptSrv: this.editForm.get(['libelleDeptSrv'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeptServices>>): void {
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
