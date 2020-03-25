import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITabAmortis, TabAmortis } from 'app/shared/model/tab-amortis.model';
import { TabAmortisService } from './tab-amortis.service';

@Component({
  selector: 'jhi-tab-amortis-update',
  templateUrl: './tab-amortis-update.component.html'
})
export class TabAmortisUpdateComponent implements OnInit {
  isSaving = false;
  dateDp: any;
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    libelle: [],
    montant: [],
    date: [],
    etat: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected tabAmortisService: TabAmortisService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tabAmortis }) => {
      this.updateForm(tabAmortis);
    });
  }

  updateForm(tabAmortis: ITabAmortis): void {
    this.editForm.patchValue({
      id: tabAmortis.id,
      libelle: tabAmortis.libelle,
      montant: tabAmortis.montant,
      date: tabAmortis.date,
      etat: tabAmortis.etat,
      dateCreated: tabAmortis.dateCreated,
      dateUpdated: tabAmortis.dateUpdated,
      dateDeleted: tabAmortis.dateDeleted,
      userCreated: tabAmortis.userCreated,
      userUpdated: tabAmortis.userUpdated,
      userDeleted: tabAmortis.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tabAmortis = this.createFromForm();
    if (tabAmortis.id !== undefined) {
      this.subscribeToSaveResponse(this.tabAmortisService.update(tabAmortis));
    } else {
      this.subscribeToSaveResponse(this.tabAmortisService.create(tabAmortis));
    }
  }

  private createFromForm(): ITabAmortis {
    return {
      ...new TabAmortis(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      montant: this.editForm.get(['montant'])!.value,
      date: this.editForm.get(['date'])!.value,
      etat: this.editForm.get(['etat'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITabAmortis>>): void {
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
