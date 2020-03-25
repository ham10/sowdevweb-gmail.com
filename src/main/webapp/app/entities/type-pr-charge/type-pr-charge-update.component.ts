import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypePrCharge, TypePrCharge } from 'app/shared/model/type-pr-charge.model';
import { TypePrChargeService } from './type-pr-charge.service';

@Component({
  selector: 'jhi-type-pr-charge-update',
  templateUrl: './type-pr-charge-update.component.html'
})
export class TypePrChargeUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypePrCharge: [],
    libelleTypePrCharge: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typePrChargeService: TypePrChargeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typePrCharge }) => {
      this.updateForm(typePrCharge);
    });
  }

  updateForm(typePrCharge: ITypePrCharge): void {
    this.editForm.patchValue({
      id: typePrCharge.id,
      codeTypePrCharge: typePrCharge.codeTypePrCharge,
      libelleTypePrCharge: typePrCharge.libelleTypePrCharge,
      dateCreated: typePrCharge.dateCreated,
      dateUpdated: typePrCharge.dateUpdated,
      userCreated: typePrCharge.userCreated,
      userUpdated: typePrCharge.userUpdated,
      userDeleted: typePrCharge.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typePrCharge = this.createFromForm();
    if (typePrCharge.id !== undefined) {
      this.subscribeToSaveResponse(this.typePrChargeService.update(typePrCharge));
    } else {
      this.subscribeToSaveResponse(this.typePrChargeService.create(typePrCharge));
    }
  }

  private createFromForm(): ITypePrCharge {
    return {
      ...new TypePrCharge(),
      id: this.editForm.get(['id'])!.value,
      codeTypePrCharge: this.editForm.get(['codeTypePrCharge'])!.value,
      libelleTypePrCharge: this.editForm.get(['libelleTypePrCharge'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypePrCharge>>): void {
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
