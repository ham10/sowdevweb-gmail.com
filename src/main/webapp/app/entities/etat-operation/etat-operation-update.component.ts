import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEtatOperation, EtatOperation } from 'app/shared/model/etat-operation.model';
import { EtatOperationService } from './etat-operation.service';

@Component({
  selector: 'jhi-etat-operation-update',
  templateUrl: './etat-operation-update.component.html'
})
export class EtatOperationUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected etatOperationService: EtatOperationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etatOperation }) => {
      this.updateForm(etatOperation);
    });
  }

  updateForm(etatOperation: IEtatOperation): void {
    this.editForm.patchValue({
      id: etatOperation.id,
      code: etatOperation.code,
      libelle: etatOperation.libelle,
      dateCreated: etatOperation.dateCreated,
      dateUpdated: etatOperation.dateUpdated,
      dateDeleted: etatOperation.dateDeleted,
      userCreated: etatOperation.userCreated,
      userUpdated: etatOperation.userUpdated,
      userDeleted: etatOperation.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etatOperation = this.createFromForm();
    if (etatOperation.id !== undefined) {
      this.subscribeToSaveResponse(this.etatOperationService.update(etatOperation));
    } else {
      this.subscribeToSaveResponse(this.etatOperationService.create(etatOperation));
    }
  }

  private createFromForm(): IEtatOperation {
    return {
      ...new EtatOperation(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtatOperation>>): void {
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
