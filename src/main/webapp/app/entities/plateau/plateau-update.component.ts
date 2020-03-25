import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPlateau, Plateau } from 'app/shared/model/plateau.model';
import { PlateauService } from './plateau.service';
import { ITypePlateau } from 'app/shared/model/type-plateau.model';
import { TypePlateauService } from 'app/entities/type-plateau/type-plateau.service';

@Component({
  selector: 'jhi-plateau-update',
  templateUrl: './plateau-update.component.html'
})
export class PlateauUpdateComponent implements OnInit {
  isSaving = false;
  typeplateaus: ITypePlateau[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    libellePlateau: [],
    descriptionPlateau: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    typePlateau: []
  });

  constructor(
    protected plateauService: PlateauService,
    protected typePlateauService: TypePlateauService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plateau }) => {
      this.updateForm(plateau);

      this.typePlateauService.query().subscribe((res: HttpResponse<ITypePlateau[]>) => (this.typeplateaus = res.body || []));
    });
  }

  updateForm(plateau: IPlateau): void {
    this.editForm.patchValue({
      id: plateau.id,
      libellePlateau: plateau.libellePlateau,
      descriptionPlateau: plateau.descriptionPlateau,
      dateCreated: plateau.dateCreated,
      dateUpdated: plateau.dateUpdated,
      userCreated: plateau.userCreated,
      userUpdated: plateau.userUpdated,
      userDeleted: plateau.userDeleted,
      typePlateau: plateau.typePlateau
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const plateau = this.createFromForm();
    if (plateau.id !== undefined) {
      this.subscribeToSaveResponse(this.plateauService.update(plateau));
    } else {
      this.subscribeToSaveResponse(this.plateauService.create(plateau));
    }
  }

  private createFromForm(): IPlateau {
    return {
      ...new Plateau(),
      id: this.editForm.get(['id'])!.value,
      libellePlateau: this.editForm.get(['libellePlateau'])!.value,
      descriptionPlateau: this.editForm.get(['descriptionPlateau'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      typePlateau: this.editForm.get(['typePlateau'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlateau>>): void {
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

  trackById(index: number, item: ITypePlateau): any {
    return item.id;
  }
}
