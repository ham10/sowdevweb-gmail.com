import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICaisse, Caisse } from 'app/shared/model/caisse.model';
import { CaisseService } from './caisse.service';

@Component({
  selector: 'jhi-caisse-update',
  templateUrl: './caisse-update.component.html'
})
export class CaisseUpdateComponent implements OnInit {
  isSaving = false;
  soldeMinDp: any;
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    numero: [],
    soldeMin: [],
    soldeMax: [],
    montant: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected caisseService: CaisseService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ caisse }) => {
      this.updateForm(caisse);
    });
  }

  updateForm(caisse: ICaisse): void {
    this.editForm.patchValue({
      id: caisse.id,
      numero: caisse.numero,
      soldeMin: caisse.soldeMin,
      soldeMax: caisse.soldeMax,
      montant: caisse.montant,
      dateCreated: caisse.dateCreated,
      dateUpdated: caisse.dateUpdated,
      dateDeleted: caisse.dateDeleted,
      userCreated: caisse.userCreated,
      userUpdated: caisse.userUpdated,
      userDeleted: caisse.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const caisse = this.createFromForm();
    if (caisse.id !== undefined) {
      this.subscribeToSaveResponse(this.caisseService.update(caisse));
    } else {
      this.subscribeToSaveResponse(this.caisseService.create(caisse));
    }
  }

  private createFromForm(): ICaisse {
    return {
      ...new Caisse(),
      id: this.editForm.get(['id'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      soldeMin: this.editForm.get(['soldeMin'])!.value,
      soldeMax: this.editForm.get(['soldeMax'])!.value,
      montant: this.editForm.get(['montant'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICaisse>>): void {
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
