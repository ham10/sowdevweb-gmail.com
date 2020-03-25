import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICodeBudget } from 'app/shared/model/code-budget.model';

@Component({
  selector: 'jhi-code-budget-detail',
  templateUrl: './code-budget-detail.component.html'
})
export class CodeBudgetDetailComponent implements OnInit {
  codeBudget: ICodeBudget | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ codeBudget }) => (this.codeBudget = codeBudget));
  }

  previousState(): void {
    window.history.back();
  }
}
