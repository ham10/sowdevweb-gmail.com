import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IItem, Item } from 'app/shared/model/item.model';
import { ItemService } from './item.service';
import { IModule } from 'app/shared/model/module.model';
import { ModuleService } from 'app/entities/module/module.service';

@Component({
  selector: 'jhi-item-update',
  templateUrl: './item-update.component.html'
})
export class ItemUpdateComponent implements OnInit {
  isSaving = false;
  modules: IModule[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    libelleItem: [],
    descriptionItem: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    module: []
  });

  constructor(
    protected itemService: ItemService,
    protected moduleService: ModuleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ item }) => {
      this.updateForm(item);

      this.moduleService.query().subscribe((res: HttpResponse<IModule[]>) => (this.modules = res.body || []));
    });
  }

  updateForm(item: IItem): void {
    this.editForm.patchValue({
      id: item.id,
      libelleItem: item.libelleItem,
      descriptionItem: item.descriptionItem,
      dateCreated: item.dateCreated,
      dateUpdated: item.dateUpdated,
      userCreated: item.userCreated,
      userUpdated: item.userUpdated,
      userDeleted: item.userDeleted,
      module: item.module
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const item = this.createFromForm();
    if (item.id !== undefined) {
      this.subscribeToSaveResponse(this.itemService.update(item));
    } else {
      this.subscribeToSaveResponse(this.itemService.create(item));
    }
  }

  private createFromForm(): IItem {
    return {
      ...new Item(),
      id: this.editForm.get(['id'])!.value,
      libelleItem: this.editForm.get(['libelleItem'])!.value,
      descriptionItem: this.editForm.get(['descriptionItem'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      module: this.editForm.get(['module'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItem>>): void {
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

  trackById(index: number, item: IModule): any {
    return item.id;
  }
}
