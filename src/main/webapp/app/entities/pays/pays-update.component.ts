import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPays, Pays } from 'app/shared/model/pays.model';
import { PaysService } from './pays.service';

@Component({
  selector: 'jhi-pays-update',
  templateUrl: './pays-update.component.html'
})
export class PaysUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codePays: [],
    libellePays: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected paysService: PaysService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pays }) => {
      this.updateForm(pays);
    });
  }

  updateForm(pays: IPays): void {
    this.editForm.patchValue({
      id: pays.id,
      codePays: pays.codePays,
      libellePays: pays.libellePays,
      dateCreated: pays.dateCreated,
      dateUpdated: pays.dateUpdated,
      userCreated: pays.userCreated,
      userUpdated: pays.userUpdated,
      userDeleted: pays.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pays = this.createFromForm();
    if (pays.id !== undefined) {
      this.subscribeToSaveResponse(this.paysService.update(pays));
    } else {
      this.subscribeToSaveResponse(this.paysService.create(pays));
    }
  }

  private createFromForm(): IPays {
    return {
      ...new Pays(),
      id: this.editForm.get(['id'])!.value,
      codePays: this.editForm.get(['codePays'])!.value,
      libellePays: this.editForm.get(['libellePays'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPays>>): void {
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
