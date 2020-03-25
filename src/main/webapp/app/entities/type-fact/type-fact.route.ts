import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeFact, TypeFact } from 'app/shared/model/type-fact.model';
import { TypeFactService } from './type-fact.service';
import { TypeFactComponent } from './type-fact.component';
import { TypeFactDetailComponent } from './type-fact-detail.component';
import { TypeFactUpdateComponent } from './type-fact-update.component';

@Injectable({ providedIn: 'root' })
export class TypeFactResolve implements Resolve<ITypeFact> {
  constructor(private service: TypeFactService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeFact> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeFact: HttpResponse<TypeFact>) => {
          if (typeFact.body) {
            return of(typeFact.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeFact());
  }
}

export const typeFactRoute: Routes = [
  {
    path: '',
    component: TypeFactComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeFact.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeFactDetailComponent,
    resolve: {
      typeFact: TypeFactResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeFact.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeFactUpdateComponent,
    resolve: {
      typeFact: TypeFactResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeFact.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeFactUpdateComponent,
    resolve: {
      typeFact: TypeFactResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeFact.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
