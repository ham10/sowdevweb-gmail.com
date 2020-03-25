import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICatChambre, CatChambre } from 'app/shared/model/cat-chambre.model';
import { CatChambreService } from './cat-chambre.service';
import { IServices } from 'app/shared/model/services.model';
import { ServicesService } from 'app/entities/services/services.service';

@Component({
  selector: 'jhi-cat-chambre-update',
  templateUrl: './cat-chambre-update.component.html'
})
export class CatChambreUpdateComponent implements OnInit {
  isSaving = false;
  services: IServices[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    libelleCatChambre: [],
    descriptionCatChambre: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    serv: []
  });

  constructor(
    protected catChambreService: CatChambreService,
    protected servicesService: ServicesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catChambre }) => {
      this.updateForm(catChambre);

      this.servicesService.query().subscribe((res: HttpResponse<IServices[]>) => (this.services = res.body || []));
    });
  }

  updateForm(catChambre: ICatChambre): void {
    this.editForm.patchValue({
      id: catChambre.id,
      libelleCatChambre: catChambre.libelleCatChambre,
      descriptionCatChambre: catChambre.descriptionCatChambre,
      dateCreated: catChambre.dateCreated,
      dateUpdated: catChambre.dateUpdated,
      userCreated: catChambre.userCreated,
      userUpdated: catChambre.userUpdated,
      userDeleted: catChambre.userDeleted,
      serv: catChambre.serv
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const catChambre = this.createFromForm();
    if (catChambre.id !== undefined) {
      this.subscribeToSaveResponse(this.catChambreService.update(catChambre));
    } else {
      this.subscribeToSaveResponse(this.catChambreService.create(catChambre));
    }
  }

  private createFromForm(): ICatChambre {
    return {
      ...new CatChambre(),
      id: this.editForm.get(['id'])!.value,
      libelleCatChambre: this.editForm.get(['libelleCatChambre'])!.value,
      descriptionCatChambre: this.editForm.get(['descriptionCatChambre'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      serv: this.editForm.get(['serv'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatChambre>>): void {
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

  trackById(index: number, item: IServices): any {
    return item.id;
  }
}
