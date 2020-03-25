import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISousFamille, SousFamille } from 'app/shared/model/sous-famille.model';
import { SousFamilleService } from './sous-famille.service';
import { IFamille } from 'app/shared/model/famille.model';
import { FamilleService } from 'app/entities/famille/famille.service';

@Component({
  selector: 'jhi-sous-famille-update',
  templateUrl: './sous-famille-update.component.html'
})
export class SousFamilleUpdateComponent implements OnInit {
  isSaving = false;
  familles: IFamille[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: [],
    description: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    famille: []
  });

  constructor(
    protected sousFamilleService: SousFamilleService,
    protected familleService: FamilleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sousFamille }) => {
      this.updateForm(sousFamille);

      this.familleService.query().subscribe((res: HttpResponse<IFamille[]>) => (this.familles = res.body || []));
    });
  }

  updateForm(sousFamille: ISousFamille): void {
    this.editForm.patchValue({
      id: sousFamille.id,
      code: sousFamille.code,
      libelle: sousFamille.libelle,
      description: sousFamille.description,
      dateCreated: sousFamille.dateCreated,
      dateUpdated: sousFamille.dateUpdated,
      dateDeleted: sousFamille.dateDeleted,
      userCreated: sousFamille.userCreated,
      userUpdated: sousFamille.userUpdated,
      userDeleted: sousFamille.userDeleted,
      famille: sousFamille.famille
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sousFamille = this.createFromForm();
    if (sousFamille.id !== undefined) {
      this.subscribeToSaveResponse(this.sousFamilleService.update(sousFamille));
    } else {
      this.subscribeToSaveResponse(this.sousFamilleService.create(sousFamille));
    }
  }

  private createFromForm(): ISousFamille {
    return {
      ...new SousFamille(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      description: this.editForm.get(['description'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      famille: this.editForm.get(['famille'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISousFamille>>): void {
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

  trackById(index: number, item: IFamille): any {
    return item.id;
  }
}
