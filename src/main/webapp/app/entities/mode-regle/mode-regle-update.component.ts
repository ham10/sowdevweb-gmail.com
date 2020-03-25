import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IModeRegle, ModeRegle } from 'app/shared/model/mode-regle.model';
import { ModeRegleService } from './mode-regle.service';

@Component({
  selector: 'jhi-mode-regle-update',
  templateUrl: './mode-regle-update.component.html'
})
export class ModeRegleUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeModeRegle: [],
    libelleModeRegle: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected modeRegleService: ModeRegleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modeRegle }) => {
      this.updateForm(modeRegle);
    });
  }

  updateForm(modeRegle: IModeRegle): void {
    this.editForm.patchValue({
      id: modeRegle.id,
      codeModeRegle: modeRegle.codeModeRegle,
      libelleModeRegle: modeRegle.libelleModeRegle,
      dateCreated: modeRegle.dateCreated,
      dateUpdated: modeRegle.dateUpdated,
      userCreated: modeRegle.userCreated,
      userUpdated: modeRegle.userUpdated,
      userDeleted: modeRegle.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const modeRegle = this.createFromForm();
    if (modeRegle.id !== undefined) {
      this.subscribeToSaveResponse(this.modeRegleService.update(modeRegle));
    } else {
      this.subscribeToSaveResponse(this.modeRegleService.create(modeRegle));
    }
  }

  private createFromForm(): IModeRegle {
    return {
      ...new ModeRegle(),
      id: this.editForm.get(['id'])!.value,
      codeModeRegle: this.editForm.get(['codeModeRegle'])!.value,
      libelleModeRegle: this.editForm.get(['libelleModeRegle'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModeRegle>>): void {
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
