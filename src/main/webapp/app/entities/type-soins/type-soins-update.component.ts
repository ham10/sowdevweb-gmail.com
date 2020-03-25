import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeSoins, TypeSoins } from 'app/shared/model/type-soins.model';
import { TypeSoinsService } from './type-soins.service';

@Component({
  selector: 'jhi-type-soins-update',
  templateUrl: './type-soins-update.component.html'
})
export class TypeSoinsUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypeSoins: [],
    libelleTypeSoins: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typeSoinsService: TypeSoinsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeSoins }) => {
      this.updateForm(typeSoins);
    });
  }

  updateForm(typeSoins: ITypeSoins): void {
    this.editForm.patchValue({
      id: typeSoins.id,
      codeTypeSoins: typeSoins.codeTypeSoins,
      libelleTypeSoins: typeSoins.libelleTypeSoins,
      dateCreated: typeSoins.dateCreated,
      dateUpdated: typeSoins.dateUpdated,
      userCreated: typeSoins.userCreated,
      userUpdated: typeSoins.userUpdated,
      userDeleted: typeSoins.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeSoins = this.createFromForm();
    if (typeSoins.id !== undefined) {
      this.subscribeToSaveResponse(this.typeSoinsService.update(typeSoins));
    } else {
      this.subscribeToSaveResponse(this.typeSoinsService.create(typeSoins));
    }
  }

  private createFromForm(): ITypeSoins {
    return {
      ...new TypeSoins(),
      id: this.editForm.get(['id'])!.value,
      codeTypeSoins: this.editForm.get(['codeTypeSoins'])!.value,
      libelleTypeSoins: this.editForm.get(['libelleTypeSoins'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeSoins>>): void {
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
