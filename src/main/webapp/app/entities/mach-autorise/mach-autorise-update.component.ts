import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMachAutorise, MachAutorise } from 'app/shared/model/mach-autorise.model';
import { MachAutoriseService } from './mach-autorise.service';

@Component({
  selector: 'jhi-mach-autorise-update',
  templateUrl: './mach-autorise-update.component.html'
})
export class MachAutoriseUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    numeroMachAutorise: [],
    adresseMacMachAutorise: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected machAutoriseService: MachAutoriseService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ machAutorise }) => {
      this.updateForm(machAutorise);
    });
  }

  updateForm(machAutorise: IMachAutorise): void {
    this.editForm.patchValue({
      id: machAutorise.id,
      numeroMachAutorise: machAutorise.numeroMachAutorise,
      adresseMacMachAutorise: machAutorise.adresseMacMachAutorise,
      dateCreated: machAutorise.dateCreated,
      dateUpdated: machAutorise.dateUpdated,
      userCreated: machAutorise.userCreated,
      userUpdated: machAutorise.userUpdated,
      userDeleted: machAutorise.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const machAutorise = this.createFromForm();
    if (machAutorise.id !== undefined) {
      this.subscribeToSaveResponse(this.machAutoriseService.update(machAutorise));
    } else {
      this.subscribeToSaveResponse(this.machAutoriseService.create(machAutorise));
    }
  }

  private createFromForm(): IMachAutorise {
    return {
      ...new MachAutorise(),
      id: this.editForm.get(['id'])!.value,
      numeroMachAutorise: this.editForm.get(['numeroMachAutorise'])!.value,
      adresseMacMachAutorise: this.editForm.get(['adresseMacMachAutorise'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMachAutorise>>): void {
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
