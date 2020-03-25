import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMonnaie, Monnaie } from 'app/shared/model/monnaie.model';
import { MonnaieService } from './monnaie.service';
import { IDevise } from 'app/shared/model/devise.model';
import { DeviseService } from 'app/entities/devise/devise.service';

@Component({
  selector: 'jhi-monnaie-update',
  templateUrl: './monnaie-update.component.html'
})
export class MonnaieUpdateComponent implements OnInit {
  isSaving = false;
  devises: IDevise[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    libelleMonnaie: [],
    montantMonnaie: [],
    natureMonnaie: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    devise: []
  });

  constructor(
    protected monnaieService: MonnaieService,
    protected deviseService: DeviseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ monnaie }) => {
      this.updateForm(monnaie);

      this.deviseService.query().subscribe((res: HttpResponse<IDevise[]>) => (this.devises = res.body || []));
    });
  }

  updateForm(monnaie: IMonnaie): void {
    this.editForm.patchValue({
      id: monnaie.id,
      libelleMonnaie: monnaie.libelleMonnaie,
      montantMonnaie: monnaie.montantMonnaie,
      natureMonnaie: monnaie.natureMonnaie,
      dateCreated: monnaie.dateCreated,
      dateUpdated: monnaie.dateUpdated,
      userCreated: monnaie.userCreated,
      userUpdated: monnaie.userUpdated,
      userDeleted: monnaie.userDeleted,
      devise: monnaie.devise
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const monnaie = this.createFromForm();
    if (monnaie.id !== undefined) {
      this.subscribeToSaveResponse(this.monnaieService.update(monnaie));
    } else {
      this.subscribeToSaveResponse(this.monnaieService.create(monnaie));
    }
  }

  private createFromForm(): IMonnaie {
    return {
      ...new Monnaie(),
      id: this.editForm.get(['id'])!.value,
      libelleMonnaie: this.editForm.get(['libelleMonnaie'])!.value,
      montantMonnaie: this.editForm.get(['montantMonnaie'])!.value,
      natureMonnaie: this.editForm.get(['natureMonnaie'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      devise: this.editForm.get(['devise'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMonnaie>>): void {
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
