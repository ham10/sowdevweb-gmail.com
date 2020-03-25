import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IActivite, Activite } from 'app/shared/model/activite.model';
import { ActiviteService } from './activite.service';

@Component({
  selector: 'jhi-activite-update',
  templateUrl: './activite-update.component.html'
})
export class ActiviteUpdateComponent implements OnInit {
  isSaving = false;
  dateActiviteDp: any;
  dateCreatedDp: any;

  editForm = this.fb.group({
    id: [],
    dateActivite: [],
    heureConnexionActivite: [],
    heureDeConnexionActivite: [],
    adresseIpActivite: [],
    dateCreated: [],
    userCreated: [],
    adresseMac: []
  });

  constructor(protected activiteService: ActiviteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ activite }) => {
      this.updateForm(activite);
    });
  }

  updateForm(activite: IActivite): void {
    this.editForm.patchValue({
      id: activite.id,
      dateActivite: activite.dateActivite,
      heureConnexionActivite: activite.heureConnexionActivite,
      heureDeConnexionActivite: activite.heureDeConnexionActivite,
      adresseIpActivite: activite.adresseIpActivite,
      dateCreated: activite.dateCreated,
      userCreated: activite.userCreated,
      adresseMac: activite.adresseMac
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const activite = this.createFromForm();
    if (activite.id !== undefined) {
      this.subscribeToSaveResponse(this.activiteService.update(activite));
    } else {
      this.subscribeToSaveResponse(this.activiteService.create(activite));
    }
  }

  private createFromForm(): IActivite {
    return {
      ...new Activite(),
      id: this.editForm.get(['id'])!.value,
      dateActivite: this.editForm.get(['dateActivite'])!.value,
      heureConnexionActivite: this.editForm.get(['heureConnexionActivite'])!.value,
      heureDeConnexionActivite: this.editForm.get(['heureDeConnexionActivite'])!.value,
      adresseIpActivite: this.editForm.get(['adresseIpActivite'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      adresseMac: this.editForm.get(['adresseMac'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActivite>>): void {
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
