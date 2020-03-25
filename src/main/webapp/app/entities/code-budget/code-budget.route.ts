import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICodeBudget, CodeBudget } from 'app/shared/model/code-budget.model';
import { CodeBudgetService } from './code-budget.service';
import { CodeBudgetComponent } from './code-budget.component';
import { CodeBudgetDetailComponent } from './code-budget-detail.component';
import { CodeBudgetUpdateComponent } from './code-budget-update.component';

@Injectable({ providedIn: 'root' })
export class CodeBudgetResolve implements Resolve<ICodeBudget> {
  constructor(private service: CodeBudgetService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICodeBudget> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((codeBudget: HttpResponse<CodeBudget>) => {
          if (codeBudget.body) {
            return of(codeBudget.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CodeBudget());
  }
}

export const codeBudgetRoute: Routes = [
  {
    path: '',
    component: CodeBudgetComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.codeBudget.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CodeBudgetDetailComponent,
    resolve: {
      codeBudget: CodeBudgetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.codeBudget.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CodeBudgetUpdateComponent,
    resolve: {
      codeBudget: CodeBudgetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.codeBudget.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CodeBudgetUpdateComponent,
    resolve: {
      codeBudget: CodeBudgetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.codeBudget.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
