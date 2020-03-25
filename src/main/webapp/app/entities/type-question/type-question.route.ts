import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeQuestion, TypeQuestion } from 'app/shared/model/type-question.model';
import { TypeQuestionService } from './type-question.service';
import { TypeQuestionComponent } from './type-question.component';
import { TypeQuestionDetailComponent } from './type-question-detail.component';
import { TypeQuestionUpdateComponent } from './type-question-update.component';

@Injectable({ providedIn: 'root' })
export class TypeQuestionResolve implements Resolve<ITypeQuestion> {
  constructor(private service: TypeQuestionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeQuestion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeQuestion: HttpResponse<TypeQuestion>) => {
          if (typeQuestion.body) {
            return of(typeQuestion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeQuestion());
  }
}

export const typeQuestionRoute: Routes = [
  {
    path: '',
    component: TypeQuestionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeQuestion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeQuestionDetailComponent,
    resolve: {
      typeQuestion: TypeQuestionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeQuestion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeQuestionUpdateComponent,
    resolve: {
      typeQuestion: TypeQuestionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeQuestion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeQuestionUpdateComponent,
    resolve: {
      typeQuestion: TypeQuestionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeQuestion.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
