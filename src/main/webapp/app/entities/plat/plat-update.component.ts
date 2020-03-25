import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPlat, Plat } from 'app/shared/model/plat.model';
import { PlatService } from './plat.service';
import { ITypePlat } from 'app/shared/model/type-plat.model';
import { TypePlatService } from 'app/entities/type-plat/type-plat.service';
import { IServices } from 'app/shared/model/services.model';
import { ServicesService } from 'app/entities/services/services.service';

type SelectableEntity = ITypePlat | IServices;

@Component({
  selector: 'jhi-plat-update',
  templateUrl: './plat-update.component.html'
})
export class PlatUpdateComponent implements OnInit {
  isSaving = false;
  typeplats: ITypePlat[] = [];
  services: IServices[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    quantite: [],
    typeRepas: [],
    date: [],
    typePlat: [],
    serv: []
  });

  constructor(
    protected platService: PlatService,
    protected typePlatService: TypePlatService,
    protected servicesService: ServicesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plat }) => {
      this.updateForm(plat);

      this.typePlatService.query().subscribe((res: HttpResponse<ITypePlat[]>) => (this.typeplats = res.body || []));

      this.servicesService.query().subscribe((res: HttpResponse<IServices[]>) => (this.services = res.body || []));
    });
  }

  updateForm(plat: IPlat): void {
    this.editForm.patchValue({
      id: plat.id,
      quantite: plat.quantite,
      typeRepas: plat.typeRepas,
      date: plat.date,
      typePlat: plat.typePlat,
      serv: plat.serv
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const plat = this.createFromForm();
    if (plat.id !== undefined) {
      this.subscribeToSaveResponse(this.platService.update(plat));
    } else {
      this.subscribeToSaveResponse(this.platService.create(plat));
    }
  }

  private createFromForm(): IPlat {
    return {
      ...new Plat(),
      id: this.editForm.get(['id'])!.value,
      quantite: this.editForm.get(['quantite'])!.value,
      typeRepas: this.editForm.get(['typeRepas'])!.value,
      date: this.editForm.get(['date'])!.value,
      typePlat: this.editForm.get(['typePlat'])!.value,
      serv: this.editForm.get(['serv'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlat>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
