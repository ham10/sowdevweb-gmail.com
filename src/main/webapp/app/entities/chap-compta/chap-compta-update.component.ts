import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IChapCompta, ChapCompta } from 'app/shared/model/chap-compta.model';
import { ChapComptaService } from './chap-compta.service';

@Component({
  selector: 'jhi-chap-compta-update',
  templateUrl: './chap-compta-update.component.html'
})
export class ChapComptaUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    numeroChapCompta: [],
    libelleChapCompta: [],
    sensChapCompta: [],
    soldeChapCompta: [],
    cumulMouvDebitChapCompta: [],
    cumulMouvCreditChapCompta: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected chapComptaService: ChapComptaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chapCompta }) => {
      this.updateForm(chapCompta);
    });
  }

  updateForm(chapCompta: IChapCompta): void {
    this.editForm.patchValue({
      id: chapCompta.id,
      numeroChapCompta: chapCompta.numeroChapCompta,
      libelleChapCompta: chapCompta.libelleChapCompta,
      sensChapCompta: chapCompta.sensChapCompta,
      soldeChapCompta: chapCompta.soldeChapCompta,
      cumulMouvDebitChapCompta: chapCompta.cumulMouvDebitChapCompta,
      cumulMouvCreditChapCompta: chapCompta.cumulMouvCreditChapCompta,
      dateCreated: chapCompta.dateCreated,
      dateUpdated: chapCompta.dateUpdated,
      dateDeleted: chapCompta.dateDeleted,
      userCreated: chapCompta.userCreated,
      userUpdated: chapCompta.userUpdated,
      userDeleted: chapCompta.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const chapCompta = this.createFromForm();
    if (chapCompta.id !== undefined) {
      this.subscribeToSaveResponse(this.chapComptaService.update(chapCompta));
    } else {
      this.subscribeToSaveResponse(this.chapComptaService.create(chapCompta));
    }
  }

  private createFromForm(): IChapCompta {
    return {
      ...new ChapCompta(),
      id: this.editForm.get(['id'])!.value,
      numeroChapCompta: this.editForm.get(['numeroChapCompta'])!.value,
      libelleChapCompta: this.editForm.get(['libelleChapCompta'])!.value,
      sensChapCompta: this.editForm.get(['sensChapCompta'])!.value,
      soldeChapCompta: this.editForm.get(['soldeChapCompta'])!.value,
      cumulMouvDebitChapCompta: this.editForm.get(['cumulMouvDebitChapCompta'])!.value,
      cumulMouvCreditChapCompta: this.editForm.get(['cumulMouvCreditChapCompta'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChapCompta>>): void {
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
