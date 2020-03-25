import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INatureOp, NatureOp } from 'app/shared/model/nature-op.model';
import { NatureOpService } from './nature-op.service';

@Component({
  selector: 'jhi-nature-op-update',
  templateUrl: './nature-op-update.component.html'
})
export class NatureOpUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    numero: [],
    libelle: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected natureOpService: NatureOpService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureOp }) => {
      this.updateForm(natureOp);
    });
  }

  updateForm(natureOp: INatureOp): void {
    this.editForm.patchValue({
      id: natureOp.id,
      numero: natureOp.numero,
      libelle: natureOp.libelle,
      dateCreated: natureOp.dateCreated,
      dateUpdated: natureOp.dateUpdated,
      dateDeleted: natureOp.dateDeleted,
      userCreated: natureOp.userCreated,
      userUpdated: natureOp.userUpdated,
      userDeleted: natureOp.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const natureOp = this.createFromForm();
    if (natureOp.id !== undefined) {
      this.subscribeToSaveResponse(this.natureOpService.update(natureOp));
    } else {
      this.subscribeToSaveResponse(this.natureOpService.create(natureOp));
    }
  }

  private createFromForm(): INatureOp {
    return {
      ...new NatureOp(),
      id: this.editForm.get(['id'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INatureOp>>): void {
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
