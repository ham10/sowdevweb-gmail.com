import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IModule, Module } from 'app/shared/model/module.model';
import { ModuleService } from './module.service';

@Component({
  selector: 'jhi-module-update',
  templateUrl: './module-update.component.html'
})
export class ModuleUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    libelleModule: [],
    descriptionModule: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected moduleService: ModuleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ module }) => {
      this.updateForm(module);
    });
  }

  updateForm(module: IModule): void {
    this.editForm.patchValue({
      id: module.id,
      libelleModule: module.libelleModule,
      descriptionModule: module.descriptionModule,
      dateCreated: module.dateCreated,
      dateUpdated: module.dateUpdated,
      userCreated: module.userCreated,
      userUpdated: module.userUpdated,
      userDeleted: module.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const module = this.createFromForm();
    if (module.id !== undefined) {
      this.subscribeToSaveResponse(this.moduleService.update(module));
    } else {
      this.subscribeToSaveResponse(this.moduleService.create(module));
    }
  }

  private createFromForm(): IModule {
    return {
      ...new Module(),
      id: this.editForm.get(['id'])!.value,
      libelleModule: this.editForm.get(['libelleModule'])!.value,
      descriptionModule: this.editForm.get(['descriptionModule'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModule>>): void {
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
