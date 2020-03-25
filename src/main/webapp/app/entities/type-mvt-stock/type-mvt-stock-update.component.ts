import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeMvtStock, TypeMvtStock } from 'app/shared/model/type-mvt-stock.model';
import { TypeMvtStockService } from './type-mvt-stock.service';
import { ITypeSortie } from 'app/shared/model/type-sortie.model';
import { TypeSortieService } from 'app/entities/type-sortie/type-sortie.service';

@Component({
  selector: 'jhi-type-mvt-stock-update',
  templateUrl: './type-mvt-stock-update.component.html'
})
export class TypeMvtStockUpdateComponent implements OnInit {
  isSaving = false;
  typesorties: ITypeSortie[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypeMvtStock: [],
    libelleTypeMvtStock: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    typeSortie: []
  });

  constructor(
    protected typeMvtStockService: TypeMvtStockService,
    protected typeSortieService: TypeSortieService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeMvtStock }) => {
      this.updateForm(typeMvtStock);

      this.typeSortieService.query().subscribe((res: HttpResponse<ITypeSortie[]>) => (this.typesorties = res.body || []));
    });
  }

  updateForm(typeMvtStock: ITypeMvtStock): void {
    this.editForm.patchValue({
      id: typeMvtStock.id,
      codeTypeMvtStock: typeMvtStock.codeTypeMvtStock,
      libelleTypeMvtStock: typeMvtStock.libelleTypeMvtStock,
      dateCreated: typeMvtStock.dateCreated,
      dateUpdated: typeMvtStock.dateUpdated,
      userCreated: typeMvtStock.userCreated,
      userUpdated: typeMvtStock.userUpdated,
      userDeleted: typeMvtStock.userDeleted,
      typeSortie: typeMvtStock.typeSortie
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeMvtStock = this.createFromForm();
    if (typeMvtStock.id !== undefined) {
      this.subscribeToSaveResponse(this.typeMvtStockService.update(typeMvtStock));
    } else {
      this.subscribeToSaveResponse(this.typeMvtStockService.create(typeMvtStock));
    }
  }

  private createFromForm(): ITypeMvtStock {
    return {
      ...new TypeMvtStock(),
      id: this.editForm.get(['id'])!.value,
      codeTypeMvtStock: this.editForm.get(['codeTypeMvtStock'])!.value,
      libelleTypeMvtStock: this.editForm.get(['libelleTypeMvtStock'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      typeSortie: this.editForm.get(['typeSortie'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeMvtStock>>): void {
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

  trackById(index: number, item: ITypeSortie): any {
    return item.id;
  }
}
