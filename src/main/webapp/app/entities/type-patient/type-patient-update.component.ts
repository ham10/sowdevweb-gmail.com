import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypePatient, TypePatient } from 'app/shared/model/type-patient.model';
import { TypePatientService } from './type-patient.service';

@Component({
  selector: 'jhi-type-patient-update',
  templateUrl: './type-patient-update.component.html'
})
export class TypePatientUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypePatient: [],
    libelleTypePatient: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typePatientService: TypePatientService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typePatient }) => {
      this.updateForm(typePatient);
    });
  }

  updateForm(typePatient: ITypePatient): void {
    this.editForm.patchValue({
      id: typePatient.id,
      codeTypePatient: typePatient.codeTypePatient,
      libelleTypePatient: typePatient.libelleTypePatient,
      dateCreated: typePatient.dateCreated,
      dateUpdated: typePatient.dateUpdated,
      userCreated: typePatient.userCreated,
      userUpdated: typePatient.userUpdated,
      userDeleted: typePatient.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typePatient = this.createFromForm();
    if (typePatient.id !== undefined) {
      this.subscribeToSaveResponse(this.typePatientService.update(typePatient));
    } else {
      this.subscribeToSaveResponse(this.typePatientService.create(typePatient));
    }
  }

  private createFromForm(): ITypePatient {
    return {
      ...new TypePatient(),
      id: this.editForm.get(['id'])!.value,
      codeTypePatient: this.editForm.get(['codeTypePatient'])!.value,
      libelleTypePatient: this.editForm.get(['libelleTypePatient'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypePatient>>): void {
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
