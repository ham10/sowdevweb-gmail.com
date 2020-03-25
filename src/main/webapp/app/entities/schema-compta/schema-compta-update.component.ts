import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ISchemaCompta, SchemaCompta } from 'app/shared/model/schema-compta.model';
import { SchemaComptaService } from './schema-compta.service';
import { IOperation } from 'app/shared/model/operation.model';
import { OperationService } from 'app/entities/operation/operation.service';
import { INatureOp } from 'app/shared/model/nature-op.model';
import { NatureOpService } from 'app/entities/nature-op/nature-op.service';

type SelectableEntity = IOperation | INatureOp;

@Component({
  selector: 'jhi-schema-compta-update',
  templateUrl: './schema-compta-update.component.html'
})
export class SchemaComptaUpdateComponent implements OnInit {
  isSaving = false;
  operations: IOperation[] = [];
  natureops: INatureOp[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: [],
    sens: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    operation: [],
    natureOperation: []
  });

  constructor(
    protected schemaComptaService: SchemaComptaService,
    protected operationService: OperationService,
    protected natureOpService: NatureOpService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ schemaCompta }) => {
      this.updateForm(schemaCompta);

      this.operationService
        .query({ filter: 'schemacomptable-is-null' })
        .pipe(
          map((res: HttpResponse<IOperation[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IOperation[]) => {
          if (!schemaCompta.operation || !schemaCompta.operation.id) {
            this.operations = resBody;
          } else {
            this.operationService
              .find(schemaCompta.operation.id)
              .pipe(
                map((subRes: HttpResponse<IOperation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IOperation[]) => (this.operations = concatRes));
          }
        });

      this.natureOpService.query().subscribe((res: HttpResponse<INatureOp[]>) => (this.natureops = res.body || []));
    });
  }

  updateForm(schemaCompta: ISchemaCompta): void {
    this.editForm.patchValue({
      id: schemaCompta.id,
      code: schemaCompta.code,
      libelle: schemaCompta.libelle,
      sens: schemaCompta.sens,
      dateCreated: schemaCompta.dateCreated,
      dateUpdated: schemaCompta.dateUpdated,
      dateDeleted: schemaCompta.dateDeleted,
      userCreated: schemaCompta.userCreated,
      userUpdated: schemaCompta.userUpdated,
      userDeleted: schemaCompta.userDeleted,
      operation: schemaCompta.operation,
      natureOperation: schemaCompta.natureOperation
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const schemaCompta = this.createFromForm();
    if (schemaCompta.id !== undefined) {
      this.subscribeToSaveResponse(this.schemaComptaService.update(schemaCompta));
    } else {
      this.subscribeToSaveResponse(this.schemaComptaService.create(schemaCompta));
    }
  }

  private createFromForm(): ISchemaCompta {
    return {
      ...new SchemaCompta(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      sens: this.editForm.get(['sens'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      operation: this.editForm.get(['operation'])!.value,
      natureOperation: this.editForm.get(['natureOperation'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchemaCompta>>): void {
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
