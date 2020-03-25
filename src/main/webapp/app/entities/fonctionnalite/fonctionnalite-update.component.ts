import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFonctionnalite, Fonctionnalite } from 'app/shared/model/fonctionnalite.model';
import { FonctionnaliteService } from './fonctionnalite.service';
import { IItem } from 'app/shared/model/item.model';
import { ItemService } from 'app/entities/item/item.service';

@Component({
  selector: 'jhi-fonctionnalite-update',
  templateUrl: './fonctionnalite-update.component.html'
})
export class FonctionnaliteUpdateComponent implements OnInit {
  isSaving = false;
  items: IItem[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    libelleFonctionnalite: [],
    descriptionFonctionnalite: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    item: []
  });

  constructor(
    protected fonctionnaliteService: FonctionnaliteService,
    protected itemService: ItemService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fonctionnalite }) => {
      this.updateForm(fonctionnalite);

      this.itemService.query().subscribe((res: HttpResponse<IItem[]>) => (this.items = res.body || []));
    });
  }

  updateForm(fonctionnalite: IFonctionnalite): void {
    this.editForm.patchValue({
      id: fonctionnalite.id,
      libelleFonctionnalite: fonctionnalite.libelleFonctionnalite,
      descriptionFonctionnalite: fonctionnalite.descriptionFonctionnalite,
      dateCreated: fonctionnalite.dateCreated,
      dateUpdated: fonctionnalite.dateUpdated,
      userCreated: fonctionnalite.userCreated,
      userUpdated: fonctionnalite.userUpdated,
      userDeleted: fonctionnalite.userDeleted,
      item: fonctionnalite.item
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fonctionnalite = this.createFromForm();
    if (fonctionnalite.id !== undefined) {
      this.subscribeToSaveResponse(this.fonctionnaliteService.update(fonctionnalite));
    } else {
      this.subscribeToSaveResponse(this.fonctionnaliteService.create(fonctionnalite));
    }
  }

  private createFromForm(): IFonctionnalite {
    return {
      ...new Fonctionnalite(),
      id: this.editForm.get(['id'])!.value,
      libelleFonctionnalite: this.editForm.get(['libelleFonctionnalite'])!.value,
      descriptionFonctionnalite: this.editForm.get(['descriptionFonctionnalite'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      item: this.editForm.get(['item'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFonctionnalite>>): void {
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

  trackById(index: number, item: IItem): any {
    return item.id;
  }
}
