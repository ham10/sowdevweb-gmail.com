import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGroupeSan, GroupeSan } from 'app/shared/model/groupe-san.model';
import { GroupeSanService } from './groupe-san.service';

@Component({
  selector: 'jhi-groupe-san-update',
  templateUrl: './groupe-san-update.component.html'
})
export class GroupeSanUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeGroupeSan: [],
    libelleGroupeSan: [],
    descriptionGroupeSan: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected groupeSanService: GroupeSanService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ groupeSan }) => {
      this.updateForm(groupeSan);
    });
  }

  updateForm(groupeSan: IGroupeSan): void {
    this.editForm.patchValue({
      id: groupeSan.id,
      codeGroupeSan: groupeSan.codeGroupeSan,
      libelleGroupeSan: groupeSan.libelleGroupeSan,
      descriptionGroupeSan: groupeSan.descriptionGroupeSan,
      dateCreated: groupeSan.dateCreated,
      dateUpdated: groupeSan.dateUpdated,
      userCreated: groupeSan.userCreated,
      userUpdated: groupeSan.userUpdated,
      userDeleted: groupeSan.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const groupeSan = this.createFromForm();
    if (groupeSan.id !== undefined) {
      this.subscribeToSaveResponse(this.groupeSanService.update(groupeSan));
    } else {
      this.subscribeToSaveResponse(this.groupeSanService.create(groupeSan));
    }
  }

  private createFromForm(): IGroupeSan {
    return {
      ...new GroupeSan(),
      id: this.editForm.get(['id'])!.value,
      codeGroupeSan: this.editForm.get(['codeGroupeSan'])!.value,
      libelleGroupeSan: this.editForm.get(['libelleGroupeSan'])!.value,
      descriptionGroupeSan: this.editForm.get(['descriptionGroupeSan'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGroupeSan>>): void {
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
