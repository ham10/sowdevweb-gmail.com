import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICatReport, CatReport } from 'app/shared/model/cat-report.model';
import { CatReportService } from './cat-report.service';

@Component({
  selector: 'jhi-cat-report-update',
  templateUrl: './cat-report-update.component.html'
})
export class CatReportUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeCategReport: [],
    libelleCategReport: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected catReportService: CatReportService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catReport }) => {
      this.updateForm(catReport);
    });
  }

  updateForm(catReport: ICatReport): void {
    this.editForm.patchValue({
      id: catReport.id,
      codeCategReport: catReport.codeCategReport,
      libelleCategReport: catReport.libelleCategReport,
      dateCreated: catReport.dateCreated,
      dateUpdated: catReport.dateUpdated,
      userCreated: catReport.userCreated,
      userUpdated: catReport.userUpdated,
      userDeleted: catReport.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const catReport = this.createFromForm();
    if (catReport.id !== undefined) {
      this.subscribeToSaveResponse(this.catReportService.update(catReport));
    } else {
      this.subscribeToSaveResponse(this.catReportService.create(catReport));
    }
  }

  private createFromForm(): ICatReport {
    return {
      ...new CatReport(),
      id: this.editForm.get(['id'])!.value,
      codeCategReport: this.editForm.get(['codeCategReport'])!.value,
      libelleCategReport: this.editForm.get(['libelleCategReport'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatReport>>): void {
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
