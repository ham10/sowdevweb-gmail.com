import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IClasseProd, ClasseProd } from 'app/shared/model/classe-prod.model';
import { ClasseProdService } from './classe-prod.service';

@Component({
  selector: 'jhi-classe-prod-update',
  templateUrl: './classe-prod-update.component.html'
})
export class ClasseProdUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeClasseProd: [],
    libelleClasseProd: [],
    descriptionClasseProd: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected classeProdService: ClasseProdService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classeProd }) => {
      this.updateForm(classeProd);
    });
  }

  updateForm(classeProd: IClasseProd): void {
    this.editForm.patchValue({
      id: classeProd.id,
      codeClasseProd: classeProd.codeClasseProd,
      libelleClasseProd: classeProd.libelleClasseProd,
      descriptionClasseProd: classeProd.descriptionClasseProd,
      dateCreated: classeProd.dateCreated,
      dateUpdated: classeProd.dateUpdated,
      userCreated: classeProd.userCreated,
      userUpdated: classeProd.userUpdated,
      userDeleted: classeProd.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const classeProd = this.createFromForm();
    if (classeProd.id !== undefined) {
      this.subscribeToSaveResponse(this.classeProdService.update(classeProd));
    } else {
      this.subscribeToSaveResponse(this.classeProdService.create(classeProd));
    }
  }

  private createFromForm(): IClasseProd {
    return {
      ...new ClasseProd(),
      id: this.editForm.get(['id'])!.value,
      codeClasseProd: this.editForm.get(['codeClasseProd'])!.value,
      libelleClasseProd: this.editForm.get(['libelleClasseProd'])!.value,
      descriptionClasseProd: this.editForm.get(['descriptionClasseProd'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClasseProd>>): void {
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
