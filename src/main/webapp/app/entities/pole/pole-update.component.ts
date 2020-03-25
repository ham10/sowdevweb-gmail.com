import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPole, Pole } from 'app/shared/model/pole.model';
import { PoleService } from './pole.service';
import { ITypePole } from 'app/shared/model/type-pole.model';
import { TypePoleService } from 'app/entities/type-pole/type-pole.service';

@Component({
  selector: 'jhi-pole-update',
  templateUrl: './pole-update.component.html'
})
export class PoleUpdateComponent implements OnInit {
  isSaving = false;
  typepoles: ITypePole[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codePole: [],
    libellePole: [],
    descriptionPole: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    typePole: []
  });

  constructor(
    protected poleService: PoleService,
    protected typePoleService: TypePoleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pole }) => {
      this.updateForm(pole);

      this.typePoleService.query().subscribe((res: HttpResponse<ITypePole[]>) => (this.typepoles = res.body || []));
    });
  }

  updateForm(pole: IPole): void {
    this.editForm.patchValue({
      id: pole.id,
      codePole: pole.codePole,
      libellePole: pole.libellePole,
      descriptionPole: pole.descriptionPole,
      dateCreated: pole.dateCreated,
      dateUpdated: pole.dateUpdated,
      userCreated: pole.userCreated,
      userUpdated: pole.userUpdated,
      userDeleted: pole.userDeleted,
      typePole: pole.typePole
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pole = this.createFromForm();
    if (pole.id !== undefined) {
      this.subscribeToSaveResponse(this.poleService.update(pole));
    } else {
      this.subscribeToSaveResponse(this.poleService.create(pole));
    }
  }

  private createFromForm(): IPole {
    return {
      ...new Pole(),
      id: this.editForm.get(['id'])!.value,
      codePole: this.editForm.get(['codePole'])!.value,
      libellePole: this.editForm.get(['libellePole'])!.value,
      descriptionPole: this.editForm.get(['descriptionPole'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      typePole: this.editForm.get(['typePole'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPole>>): void {
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

  trackById(index: number, item: ITypePole): any {
    return item.id;
  }
}
