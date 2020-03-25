import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEtagere, Etagere } from 'app/shared/model/etagere.model';
import { EtagereService } from './etagere.service';
import { IRayon } from 'app/shared/model/rayon.model';
import { RayonService } from 'app/entities/rayon/rayon.service';

@Component({
  selector: 'jhi-etagere-update',
  templateUrl: './etagere-update.component.html'
})
export class EtagereUpdateComponent implements OnInit {
  isSaving = false;
  rayons: IRayon[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: [],
    rayon: []
  });

  constructor(
    protected etagereService: EtagereService,
    protected rayonService: RayonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etagere }) => {
      this.updateForm(etagere);

      this.rayonService.query().subscribe((res: HttpResponse<IRayon[]>) => (this.rayons = res.body || []));
    });
  }

  updateForm(etagere: IEtagere): void {
    this.editForm.patchValue({
      id: etagere.id,
      code: etagere.code,
      libelle: etagere.libelle,
      rayon: etagere.rayon
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etagere = this.createFromForm();
    if (etagere.id !== undefined) {
      this.subscribeToSaveResponse(this.etagereService.update(etagere));
    } else {
      this.subscribeToSaveResponse(this.etagereService.create(etagere));
    }
  }

  private createFromForm(): IEtagere {
    return {
      ...new Etagere(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      rayon: this.editForm.get(['rayon'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtagere>>): void {
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

  trackById(index: number, item: IRayon): any {
    return item.id;
  }
}
