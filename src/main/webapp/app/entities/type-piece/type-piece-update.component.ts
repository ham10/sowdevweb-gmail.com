import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypePiece, TypePiece } from 'app/shared/model/type-piece.model';
import { TypePieceService } from './type-piece.service';

@Component({
  selector: 'jhi-type-piece-update',
  templateUrl: './type-piece-update.component.html'
})
export class TypePieceUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeTypePiece: [],
    libelleTypePiece: [],
    descriptionTypePiece: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected typePieceService: TypePieceService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typePiece }) => {
      this.updateForm(typePiece);
    });
  }

  updateForm(typePiece: ITypePiece): void {
    this.editForm.patchValue({
      id: typePiece.id,
      codeTypePiece: typePiece.codeTypePiece,
      libelleTypePiece: typePiece.libelleTypePiece,
      descriptionTypePiece: typePiece.descriptionTypePiece,
      dateCreated: typePiece.dateCreated,
      dateUpdated: typePiece.dateUpdated,
      userCreated: typePiece.userCreated,
      userUpdated: typePiece.userUpdated,
      userDeleted: typePiece.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typePiece = this.createFromForm();
    if (typePiece.id !== undefined) {
      this.subscribeToSaveResponse(this.typePieceService.update(typePiece));
    } else {
      this.subscribeToSaveResponse(this.typePieceService.create(typePiece));
    }
  }

  private createFromForm(): ITypePiece {
    return {
      ...new TypePiece(),
      id: this.editForm.get(['id'])!.value,
      codeTypePiece: this.editForm.get(['codeTypePiece'])!.value,
      libelleTypePiece: this.editForm.get(['libelleTypePiece'])!.value,
      descriptionTypePiece: this.editForm.get(['descriptionTypePiece'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypePiece>>): void {
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
}
