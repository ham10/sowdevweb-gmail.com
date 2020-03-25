import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFormeProd, FormeProd } from 'app/shared/model/forme-prod.model';
import { FormeProdService } from './forme-prod.service';

@Component({
  selector: 'jhi-forme-prod-update',
  templateUrl: './forme-prod-update.component.html'
})
export class FormeProdUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeFormeProd: [],
    libelleFormeProd: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected formeProdService: FormeProdService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formeProd }) => {
      this.updateForm(formeProd);
    });
  }

  updateForm(formeProd: IFormeProd): void {
    this.editForm.patchValue({
      id: formeProd.id,
      codeFormeProd: formeProd.codeFormeProd,
      libelleFormeProd: formeProd.libelleFormeProd,
      dateCreated: formeProd.dateCreated,
      dateUpdated: formeProd.dateUpdated,
      userCreated: formeProd.userCreated,
      userUpdated: formeProd.userUpdated,
      userDeleted: formeProd.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const formeProd = this.createFromForm();
    if (formeProd.id !== undefined) {
      this.subscribeToSaveResponse(this.formeProdService.update(formeProd));
    } else {
      this.subscribeToSaveResponse(this.formeProdService.create(formeProd));
    }
  }

  private createFromForm(): IFormeProd {
    return {
      ...new FormeProd(),
      id: this.editForm.get(['id'])!.value,
      codeFormeProd: this.editForm.get(['codeFormeProd'])!.value,
      libelleFormeProd: this.editForm.get(['libelleFormeProd'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormeProd>>): void {
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
