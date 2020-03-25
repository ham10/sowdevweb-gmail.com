import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITauxDevise, TauxDevise } from 'app/shared/model/taux-devise.model';
import { TauxDeviseService } from './taux-devise.service';
import { IDevise } from 'app/shared/model/devise.model';
import { DeviseService } from 'app/entities/devise/devise.service';

@Component({
  selector: 'jhi-taux-devise-update',
  templateUrl: './taux-devise-update.component.html'
})
export class TauxDeviseUpdateComponent implements OnInit {
  isSaving = false;
  devises: IDevise[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    sensTauxDevise: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    devise: []
  });

  constructor(
    protected tauxDeviseService: TauxDeviseService,
    protected deviseService: DeviseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tauxDevise }) => {
      this.updateForm(tauxDevise);

      this.deviseService.query().subscribe((res: HttpResponse<IDevise[]>) => (this.devises = res.body || []));
    });
  }

  updateForm(tauxDevise: ITauxDevise): void {
    this.editForm.patchValue({
      id: tauxDevise.id,
      sensTauxDevise: tauxDevise.sensTauxDevise,
      dateCreated: tauxDevise.dateCreated,
      dateUpdated: tauxDevise.dateUpdated,
      userCreated: tauxDevise.userCreated,
      userUpdated: tauxDevise.userUpdated,
      userDeleted: tauxDevise.userDeleted,
      devise: tauxDevise.devise
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tauxDevise = this.createFromForm();
    if (tauxDevise.id !== undefined) {
      this.subscribeToSaveResponse(this.tauxDeviseService.update(tauxDevise));
    } else {
      this.subscribeToSaveResponse(this.tauxDeviseService.create(tauxDevise));
    }
  }

  private createFromForm(): ITauxDevise {
    return {
      ...new TauxDevise(),
      id: this.editForm.get(['id'])!.value,
      sensTauxDevise: this.editForm.get(['sensTauxDevise'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      devise: this.editForm.get(['devise'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITauxDevise>>): void {
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

  trackById(index: number, item: IDevise): any {
    return item.id;
  }
}
