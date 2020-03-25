import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IActeMedical, ActeMedical } from 'app/shared/model/acte-medical.model';
import { ActeMedicalService } from './acte-medical.service';

@Component({
  selector: 'jhi-acte-medical-update',
  templateUrl: './acte-medical-update.component.html'
})
export class ActeMedicalUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    code: [],
    libelleA: [],
    description: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected acteMedicalService: ActeMedicalService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ acteMedical }) => {
      this.updateForm(acteMedical);
    });
  }

  updateForm(acteMedical: IActeMedical): void {
    this.editForm.patchValue({
      id: acteMedical.id,
      code: acteMedical.code,
      libelleA: acteMedical.libelleA,
      description: acteMedical.description,
      dateCreated: acteMedical.dateCreated,
      dateUpdated: acteMedical.dateUpdated,
      userCreated: acteMedical.userCreated,
      userUpdated: acteMedical.userUpdated,
      userDeleted: acteMedical.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const acteMedical = this.createFromForm();
    if (acteMedical.id !== undefined) {
      this.subscribeToSaveResponse(this.acteMedicalService.update(acteMedical));
    } else {
      this.subscribeToSaveResponse(this.acteMedicalService.create(acteMedical));
    }
  }

  private createFromForm(): IActeMedical {
    return {
      ...new ActeMedical(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelleA: this.editForm.get(['libelleA'])!.value,
      description: this.editForm.get(['description'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActeMedical>>): void {
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
