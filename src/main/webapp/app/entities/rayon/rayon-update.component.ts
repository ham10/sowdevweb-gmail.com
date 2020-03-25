import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRayon, Rayon } from 'app/shared/model/rayon.model';
import { RayonService } from './rayon.service';
import { IDepot } from 'app/shared/model/depot.model';
import { DepotService } from 'app/entities/depot/depot.service';

@Component({
  selector: 'jhi-rayon-update',
  templateUrl: './rayon-update.component.html'
})
export class RayonUpdateComponent implements OnInit {
  isSaving = false;
  depots: IDepot[] = [];

  editForm = this.fb.group({
    id: [],
    codeRayon: [],
    libelleRayon: [],
    rayon: []
  });

  constructor(
    protected rayonService: RayonService,
    protected depotService: DepotService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rayon }) => {
      this.updateForm(rayon);

      this.depotService.query().subscribe((res: HttpResponse<IDepot[]>) => (this.depots = res.body || []));
    });
  }

  updateForm(rayon: IRayon): void {
    this.editForm.patchValue({
      id: rayon.id,
      codeRayon: rayon.codeRayon,
      libelleRayon: rayon.libelleRayon,
      rayon: rayon.rayon
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rayon = this.createFromForm();
    if (rayon.id !== undefined) {
      this.subscribeToSaveResponse(this.rayonService.update(rayon));
    } else {
      this.subscribeToSaveResponse(this.rayonService.create(rayon));
    }
  }

  private createFromForm(): IRayon {
    return {
      ...new Rayon(),
      id: this.editForm.get(['id'])!.value,
      codeRayon: this.editForm.get(['codeRayon'])!.value,
      libelleRayon: this.editForm.get(['libelleRayon'])!.value,
      rayon: this.editForm.get(['rayon'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRayon>>): void {
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

  trackById(index: number, item: IDepot): any {
    return item.id;
  }
}
