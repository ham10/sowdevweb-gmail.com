import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeBonCom, TypeBonCom } from 'app/shared/model/type-bon-com.model';
import { TypeBonComService } from './type-bon-com.service';

@Component({
  selector: 'jhi-type-bon-com-update',
  templateUrl: './type-bon-com-update.component.html'
})
export class TypeBonComUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    libelleTypeBonCom: [],
    codeTypeBonCom: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typeBonComService: TypeBonComService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeBonCom }) => {
      this.updateForm(typeBonCom);
    });
  }

  updateForm(typeBonCom: ITypeBonCom): void {
    this.editForm.patchValue({
      id: typeBonCom.id,
      libelleTypeBonCom: typeBonCom.libelleTypeBonCom,
      codeTypeBonCom: typeBonCom.codeTypeBonCom,
      dateCreated: typeBonCom.dateCreated,
      dateUpdated: typeBonCom.dateUpdated,
      userCreated: typeBonCom.userCreated,
      userUpdated: typeBonCom.userUpdated,
      userDeleted: typeBonCom.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeBonCom = this.createFromForm();
    if (typeBonCom.id !== undefined) {
      this.subscribeToSaveResponse(this.typeBonComService.update(typeBonCom));
    } else {
      this.subscribeToSaveResponse(this.typeBonComService.create(typeBonCom));
    }
  }

  private createFromForm(): ITypeBonCom {
    return {
      ...new TypeBonCom(),
      id: this.editForm.get(['id'])!.value,
      libelleTypeBonCom: this.editForm.get(['libelleTypeBonCom'])!.value,
      codeTypeBonCom: this.editForm.get(['codeTypeBonCom'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeBonCom>>): void {
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
