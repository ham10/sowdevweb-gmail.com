import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeServices, TypeServices } from 'app/shared/model/type-services.model';
import { TypeServicesService } from './type-services.service';

@Component({
  selector: 'jhi-type-services-update',
  templateUrl: './type-services-update.component.html'
})
export class TypeServicesUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypeSrv: [],
    libelleTypeSrv: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typeServicesService: TypeServicesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeServices }) => {
      this.updateForm(typeServices);
    });
  }

  updateForm(typeServices: ITypeServices): void {
    this.editForm.patchValue({
      id: typeServices.id,
      codeTypeSrv: typeServices.codeTypeSrv,
      libelleTypeSrv: typeServices.libelleTypeSrv,
      dateCreated: typeServices.dateCreated,
      dateUpdated: typeServices.dateUpdated,
      userCreated: typeServices.userCreated,
      userUpdated: typeServices.userUpdated,
      userDeleted: typeServices.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeServices = this.createFromForm();
    if (typeServices.id !== undefined) {
      this.subscribeToSaveResponse(this.typeServicesService.update(typeServices));
    } else {
      this.subscribeToSaveResponse(this.typeServicesService.create(typeServices));
    }
  }

  private createFromForm(): ITypeServices {
    return {
      ...new TypeServices(),
      id: this.editForm.get(['id'])!.value,
      codeTypeSrv: this.editForm.get(['codeTypeSrv'])!.value,
      libelleTypeSrv: this.editForm.get(['libelleTypeSrv'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeServices>>): void {
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
