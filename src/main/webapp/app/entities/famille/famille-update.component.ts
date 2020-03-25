import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFamille, Famille } from 'app/shared/model/famille.model';
import { FamilleService } from './famille.service';
import { ITypeTarif } from 'app/shared/model/type-tarif.model';
import { TypeTarifService } from 'app/entities/type-tarif/type-tarif.service';

@Component({
  selector: 'jhi-famille-update',
  templateUrl: './famille-update.component.html'
})
export class FamilleUpdateComponent implements OnInit {
  isSaving = false;
  typetarifs: ITypeTarif[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    typeTarif: []
  });

  constructor(
    protected familleService: FamilleService,
    protected typeTarifService: TypeTarifService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ famille }) => {
      this.updateForm(famille);

      this.typeTarifService.query().subscribe((res: HttpResponse<ITypeTarif[]>) => (this.typetarifs = res.body || []));
    });
  }

  updateForm(famille: IFamille): void {
    this.editForm.patchValue({
      id: famille.id,
      code: famille.code,
      libelle: famille.libelle,
      dateCreated: famille.dateCreated,
      dateUpdated: famille.dateUpdated,
      dateDeleted: famille.dateDeleted,
      userCreated: famille.userCreated,
      userUpdated: famille.userUpdated,
      userDeleted: famille.userDeleted,
      typeTarif: famille.typeTarif
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const famille = this.createFromForm();
    if (famille.id !== undefined) {
      this.subscribeToSaveResponse(this.familleService.update(famille));
    } else {
      this.subscribeToSaveResponse(this.familleService.create(famille));
    }
  }

  private createFromForm(): IFamille {
    return {
      ...new Famille(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      typeTarif: this.editForm.get(['typeTarif'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFamille>>): void {
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

  trackById(index: number, item: ITypeTarif): any {
    return item.id;
  }
}
