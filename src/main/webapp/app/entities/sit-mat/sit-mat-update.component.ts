import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISitMat, SitMat } from 'app/shared/model/sit-mat.model';
import { SitMatService } from './sit-mat.service';

@Component({
  selector: 'jhi-sit-mat-update',
  templateUrl: './sit-mat-update.component.html'
})
export class SitMatUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    codeSitMat: [],
    libelleSitMat: []
  });

  constructor(protected sitMatService: SitMatService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sitMat }) => {
      this.updateForm(sitMat);
    });
  }

  updateForm(sitMat: ISitMat): void {
    this.editForm.patchValue({
      id: sitMat.id,
      codeSitMat: sitMat.codeSitMat,
      libelleSitMat: sitMat.libelleSitMat
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sitMat = this.createFromForm();
    if (sitMat.id !== undefined) {
      this.subscribeToSaveResponse(this.sitMatService.update(sitMat));
    } else {
      this.subscribeToSaveResponse(this.sitMatService.create(sitMat));
    }
  }

  private createFromForm(): ISitMat {
    return {
      ...new SitMat(),
      id: this.editForm.get(['id'])!.value,
      codeSitMat: this.editForm.get(['codeSitMat'])!.value,
      libelleSitMat: this.editForm.get(['libelleSitMat'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISitMat>>): void {
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
