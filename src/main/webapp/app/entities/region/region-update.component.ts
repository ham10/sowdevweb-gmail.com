import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRegion, Region } from 'app/shared/model/region.model';
import { RegionService } from './region.service';
import { IPays } from 'app/shared/model/pays.model';
import { PaysService } from 'app/entities/pays/pays.service';

@Component({
  selector: 'jhi-region-update',
  templateUrl: './region-update.component.html'
})
export class RegionUpdateComponent implements OnInit {
  isSaving = false;
  pays: IPays[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeRegion: [],
    libelleRegion: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    pays: []
  });

  constructor(
    protected regionService: RegionService,
    protected paysService: PaysService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ region }) => {
      this.updateForm(region);

      this.paysService.query().subscribe((res: HttpResponse<IPays[]>) => (this.pays = res.body || []));
    });
  }

  updateForm(region: IRegion): void {
    this.editForm.patchValue({
      id: region.id,
      codeRegion: region.codeRegion,
      libelleRegion: region.libelleRegion,
      dateCreated: region.dateCreated,
      dateUpdated: region.dateUpdated,
      userCreated: region.userCreated,
      userUpdated: region.userUpdated,
      userDeleted: region.userDeleted,
      pays: region.pays
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const region = this.createFromForm();
    if (region.id !== undefined) {
      this.subscribeToSaveResponse(this.regionService.update(region));
    } else {
      this.subscribeToSaveResponse(this.regionService.create(region));
    }
  }

  private createFromForm(): IRegion {
    return {
      ...new Region(),
      id: this.editForm.get(['id'])!.value,
      codeRegion: this.editForm.get(['codeRegion'])!.value,
      libelleRegion: this.editForm.get(['libelleRegion'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      pays: this.editForm.get(['pays'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRegion>>): void {
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

  trackById(index: number, item: IPays): any {
    return item.id;
  }
}
