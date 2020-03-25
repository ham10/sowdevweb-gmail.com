import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICodeBudget, CodeBudget } from 'app/shared/model/code-budget.model';
import { CodeBudgetService } from './code-budget.service';
import { ICompteGene } from 'app/shared/model/compte-gene.model';
import { CompteGeneService } from 'app/entities/compte-gene/compte-gene.service';

@Component({
  selector: 'jhi-code-budget-update',
  templateUrl: './code-budget-update.component.html'
})
export class CodeBudgetUpdateComponent implements OnInit {
  isSaving = false;
  comptegenes: ICompteGene[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    codeCodeBudget: [],
    libelleCodeBudget: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    compteGeneral: []
  });

  constructor(
    protected codeBudgetService: CodeBudgetService,
    protected compteGeneService: CompteGeneService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ codeBudget }) => {
      this.updateForm(codeBudget);

      this.compteGeneService.query().subscribe((res: HttpResponse<ICompteGene[]>) => (this.comptegenes = res.body || []));
    });
  }

  updateForm(codeBudget: ICodeBudget): void {
    this.editForm.patchValue({
      id: codeBudget.id,
      codeCodeBudget: codeBudget.codeCodeBudget,
      libelleCodeBudget: codeBudget.libelleCodeBudget,
      dateCreated: codeBudget.dateCreated,
      dateUpdated: codeBudget.dateUpdated,
      dateDeleted: codeBudget.dateDeleted,
      userCreated: codeBudget.userCreated,
      userUpdated: codeBudget.userUpdated,
      userDeleted: codeBudget.userDeleted,
      compteGeneral: codeBudget.compteGeneral
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const codeBudget = this.createFromForm();
    if (codeBudget.id !== undefined) {
      this.subscribeToSaveResponse(this.codeBudgetService.update(codeBudget));
    } else {
      this.subscribeToSaveResponse(this.codeBudgetService.create(codeBudget));
    }
  }

  private createFromForm(): ICodeBudget {
    return {
      ...new CodeBudget(),
      id: this.editForm.get(['id'])!.value,
      codeCodeBudget: this.editForm.get(['codeCodeBudget'])!.value,
      libelleCodeBudget: this.editForm.get(['libelleCodeBudget'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      compteGeneral: this.editForm.get(['compteGeneral'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICodeBudget>>): void {
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

  trackById(index: number, item: ICompteGene): any {
    return item.id;
  }
}
