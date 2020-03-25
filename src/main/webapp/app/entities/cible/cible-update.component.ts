import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICible, Cible } from 'app/shared/model/cible.model';
import { CibleService } from './cible.service';

@Component({
  selector: 'jhi-cible-update',
  templateUrl: './cible-update.component.html'
})
export class CibleUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelleCible: [],
    idUser: [],
    dateDeleted: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdate: [],
    userDelete: []
  });

  constructor(protected cibleService: CibleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cible }) => {
      if (!cible.id) {
        const today = moment().startOf('day');
        cible.dateDeleted = today;
        cible.dateCreated = today;
        cible.dateUpdated = today;
      }

      this.updateForm(cible);
    });
  }

  updateForm(cible: ICible): void {
    this.editForm.patchValue({
      id: cible.id,
      libelleCible: cible.libelleCible,
      idUser: cible.idUser,
      dateDeleted: cible.dateDeleted ? cible.dateDeleted.format(DATE_TIME_FORMAT) : null,
      dateCreated: cible.dateCreated ? cible.dateCreated.format(DATE_TIME_FORMAT) : null,
      dateUpdated: cible.dateUpdated ? cible.dateUpdated.format(DATE_TIME_FORMAT) : null,
      userCreated: cible.userCreated,
      userUpdate: cible.userUpdate,
      userDelete: cible.userDelete
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cible = this.createFromForm();
    if (cible.id !== undefined) {
      this.subscribeToSaveResponse(this.cibleService.update(cible));
    } else {
      this.subscribeToSaveResponse(this.cibleService.create(cible));
    }
  }

  private createFromForm(): ICible {
    return {
      ...new Cible(),
      id: this.editForm.get(['id'])!.value,
      libelleCible: this.editForm.get(['libelleCible'])!.value,
      idUser: this.editForm.get(['idUser'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value
        ? moment(this.editForm.get(['dateDeleted'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dateCreated: this.editForm.get(['dateCreated'])!.value
        ? moment(this.editForm.get(['dateCreated'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value
        ? moment(this.editForm.get(['dateUpdated'])!.value, DATE_TIME_FORMAT)
        : undefined,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdate: this.editForm.get(['userUpdate'])!.value,
      userDelete: this.editForm.get(['userDelete'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICible>>): void {
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
