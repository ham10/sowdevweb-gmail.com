import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILit, Lit } from 'app/shared/model/lit.model';
import { LitService } from './lit.service';
import { ITypeLit } from 'app/shared/model/type-lit.model';
import { TypeLitService } from 'app/entities/type-lit/type-lit.service';
import { IBoxe } from 'app/shared/model/boxe.model';
import { BoxeService } from 'app/entities/boxe/boxe.service';

type SelectableEntity = ITypeLit | IBoxe;

@Component({
  selector: 'jhi-lit-update',
  templateUrl: './lit-update.component.html'
})
export class LitUpdateComponent implements OnInit {
  isSaving = false;
  typelits: ITypeLit[] = [];
  boxes: IBoxe[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    numeroLit: [],
    descriptionLit: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    typeLit: [],
    box: []
  });

  constructor(
    protected litService: LitService,
    protected typeLitService: TypeLitService,
    protected boxeService: BoxeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lit }) => {
      this.updateForm(lit);

      this.typeLitService.query().subscribe((res: HttpResponse<ITypeLit[]>) => (this.typelits = res.body || []));

      this.boxeService.query().subscribe((res: HttpResponse<IBoxe[]>) => (this.boxes = res.body || []));
    });
  }

  updateForm(lit: ILit): void {
    this.editForm.patchValue({
      id: lit.id,
      numeroLit: lit.numeroLit,
      descriptionLit: lit.descriptionLit,
      dateCreated: lit.dateCreated,
      dateUpdated: lit.dateUpdated,
      userCreated: lit.userCreated,
      userUpdated: lit.userUpdated,
      userDeleted: lit.userDeleted,
      typeLit: lit.typeLit,
      box: lit.box
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lit = this.createFromForm();
    if (lit.id !== undefined) {
      this.subscribeToSaveResponse(this.litService.update(lit));
    } else {
      this.subscribeToSaveResponse(this.litService.create(lit));
    }
  }

  private createFromForm(): ILit {
    return {
      ...new Lit(),
      id: this.editForm.get(['id'])!.value,
      numeroLit: this.editForm.get(['numeroLit'])!.value,
      descriptionLit: this.editForm.get(['descriptionLit'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      typeLit: this.editForm.get(['typeLit'])!.value,
      box: this.editForm.get(['box'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILit>>): void {
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
