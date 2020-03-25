import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { CodeBudgetComponent } from './code-budget.component';
import { CodeBudgetDetailComponent } from './code-budget-detail.component';
import { CodeBudgetUpdateComponent } from './code-budget-update.component';
import { CodeBudgetDeleteDialogComponent } from './code-budget-delete-dialog.component';
import { codeBudgetRoute } from './code-budget.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(codeBudgetRoute)],
  declarations: [CodeBudgetComponent, CodeBudgetDetailComponent, CodeBudgetUpdateComponent, CodeBudgetDeleteDialogComponent],
  entryComponents: [CodeBudgetDeleteDialogComponent]
})
export class HpdCodeBudgetModule {}
