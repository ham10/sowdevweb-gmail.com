import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeFournis, TypeFournis } from 'app/shared/model/type-fournis.model';
import { TypeFournisService } from './type-fournis.service';

@Component({
  selector: 'jhi-type-fournis-update',
  templateUrl: './type-fournis-update.component.html'
})
export class TypeFournisUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypeFournis: [],
    libelleTypeFournis: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typeFournisService: TypeFournisService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeFournis }) => {
      this.updateForm(typeFournis);
    });
  }

  updateForm(typeFournis: ITypeFournis): void {
    this.editForm.patchValue({
      id: typeFournis.id,
      codeTypeFournis: typeFournis.codeTypeFournis,
      libelleTypeFournis: typeFournis.libelleTypeFournis,
      dateCreated: typeFournis.dateCreated,
      dateUpdated: typeFournis.dateUpdated,
      userCreated: typeFournis.userCreated,
      userUpdated: typeFournis.userUpdated,
      userDeleted: typeFournis.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeFournis = this.createFromForm();
    if (typeFournis.id !== undefined) {
      this.subscribeToSaveResponse(this.typeFournisService.update(typeFournis));
    } else {
      this.subscribeToSaveResponse(this.typeFournisService.create(typeFournis));
    }
  }

  private createFromForm(): ITypeFournis {
    return {
      ...new TypeFournis(),
      id: this.editForm.get(['id'])!.value,
      codeTypeFournis: this.editForm.get(['codeTypeFournis'])!.value,
      libelleTypeFournis: this.editForm.get(['libelleTypeFournis'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeFournis>>): void {
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
