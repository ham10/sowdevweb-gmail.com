import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeFact, TypeFact } from 'app/shared/model/type-fact.model';
import { TypeFactService } from './type-fact.service';

@Component({
  selector: 'jhi-type-fact-update',
  templateUrl: './type-fact-update.component.html'
})
export class TypeFactUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypeFact: [],
    libelleTypeFact: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typeFactService: TypeFactService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeFact }) => {
      this.updateForm(typeFact);
    });
  }

  updateForm(typeFact: ITypeFact): void {
    this.editForm.patchValue({
      id: typeFact.id,
      codeTypeFact: typeFact.codeTypeFact,
      libelleTypeFact: typeFact.libelleTypeFact,
      dateCreated: typeFact.dateCreated,
      dateUpdated: typeFact.dateUpdated,
      userCreated: typeFact.userCreated,
      userUpdated: typeFact.userUpdated,
      userDeleted: typeFact.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeFact = this.createFromForm();
    if (typeFact.id !== undefined) {
      this.subscribeToSaveResponse(this.typeFactService.update(typeFact));
    } else {
      this.subscribeToSaveResponse(this.typeFactService.create(typeFact));
    }
  }

  private createFromForm(): ITypeFact {
    return {
      ...new TypeFact(),
      id: this.editForm.get(['id'])!.value,
      codeTypeFact: this.editForm.get(['codeTypeFact'])!.value,
      libelleTypeFact: this.editForm.get(['libelleTypeFact'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeFact>>): void {
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
