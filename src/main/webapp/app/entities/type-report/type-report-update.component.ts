import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeReport, TypeReport } from 'app/shared/model/type-report.model';
import { TypeReportService } from './type-report.service';

@Component({
  selector: 'jhi-type-report-update',
  templateUrl: './type-report-update.component.html'
})
export class TypeReportUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypeReport: [],
    libelleTypeReport: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typeReportService: TypeReportService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeReport }) => {
      this.updateForm(typeReport);
    });
  }

  updateForm(typeReport: ITypeReport): void {
    this.editForm.patchValue({
      id: typeReport.id,
      codeTypeReport: typeReport.codeTypeReport,
      libelleTypeReport: typeReport.libelleTypeReport,
      dateCreated: typeReport.dateCreated,
      dateUpdated: typeReport.dateUpdated,
      userCreated: typeReport.userCreated,
      userUpdated: typeReport.userUpdated,
      userDeleted: typeReport.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeReport = this.createFromForm();
    if (typeReport.id !== undefined) {
      this.subscribeToSaveResponse(this.typeReportService.update(typeReport));
    } else {
      this.subscribeToSaveResponse(this.typeReportService.create(typeReport));
    }
  }

  private createFromForm(): ITypeReport {
    return {
      ...new TypeReport(),
      id: this.editForm.get(['id'])!.value,
      codeTypeReport: this.editForm.get(['codeTypeReport'])!.value,
      libelleTypeReport: this.editForm.get(['libelleTypeReport'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeReport>>): void {
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
