import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeCond, TypeCond } from 'app/shared/model/type-cond.model';
import { TypeCondService } from './type-cond.service';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit/produit.service';

@Component({
  selector: 'jhi-type-cond-update',
  templateUrl: './type-cond-update.component.html'
})
export class TypeCondUpdateComponent implements OnInit {
  isSaving = false;
  produits: IProduit[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: [],
    produit: []
  });

  constructor(
    protected typeCondService: TypeCondService,
    protected produitService: ProduitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeCond }) => {
      this.updateForm(typeCond);

      this.produitService.query().subscribe((res: HttpResponse<IProduit[]>) => (this.produits = res.body || []));
    });
  }

  updateForm(typeCond: ITypeCond): void {
    this.editForm.patchValue({
      id: typeCond.id,
      code: typeCond.code,
      libelle: typeCond.libelle,
      produit: typeCond.produit
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeCond = this.createFromForm();
    if (typeCond.id !== undefined) {
      this.subscribeToSaveResponse(this.typeCondService.update(typeCond));
    } else {
      this.subscribeToSaveResponse(this.typeCondService.create(typeCond));
    }
  }

  private createFromForm(): ITypeCond {
    return {
      ...new TypeCond(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      produit: this.editForm.get(['produit'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeCond>>): void {
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

  trackById(index: number, item: IProduit): any {
    return item.id;
  }
}
