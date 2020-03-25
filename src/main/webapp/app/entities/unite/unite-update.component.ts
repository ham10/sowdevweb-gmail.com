import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUnite, Unite } from 'app/shared/model/unite.model';
import { UniteService } from './unite.service';
import { ITypeUnite } from 'app/shared/model/type-unite.model';
import { TypeUniteService } from 'app/entities/type-unite/type-unite.service';

@Component({
  selector: 'jhi-unite-update',
  templateUrl: './unite-update.component.html'
})
export class UniteUpdateComponent implements OnInit {
  isSaving = false;
  typeunites: ITypeUnite[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeUnite: [],
    libelleUnite: [],
    descriptionUnite: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    typeUnite: []
  });

  constructor(
    protected uniteService: UniteService,
    protected typeUniteService: TypeUniteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ unite }) => {
      this.updateForm(unite);

      this.typeUniteService.query().subscribe((res: HttpResponse<ITypeUnite[]>) => (this.typeunites = res.body || []));
    });
  }

  updateForm(unite: IUnite): void {
    this.editForm.patchValue({
      id: unite.id,
      codeUnite: unite.codeUnite,
      libelleUnite: unite.libelleUnite,
      descriptionUnite: unite.descriptionUnite,
      dateCreated: unite.dateCreated,
      dateUpdated: unite.dateUpdated,
      userCreated: unite.userCreated,
      userUpdated: unite.userUpdated,
      userDeleted: unite.userDeleted,
      typeUnite: unite.typeUnite
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const unite = this.createFromForm();
    if (unite.id !== undefined) {
      this.subscribeToSaveResponse(this.uniteService.update(unite));
    } else {
      this.subscribeToSaveResponse(this.uniteService.create(unite));
    }
  }

  private createFromForm(): IUnite {
    return {
      ...new Unite(),
      id: this.editForm.get(['id'])!.value,
      codeUnite: this.editForm.get(['codeUnite'])!.value,
      libelleUnite: this.editForm.get(['libelleUnite'])!.value,
      descriptionUnite: this.editForm.get(['descriptionUnite'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      typeUnite: this.editForm.get(['typeUnite'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUnite>>): void {
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

  trackById(index: number, item: ITypeUnite): any {
    return item.id;
  }
}
