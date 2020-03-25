import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeCond, TypeCond } from 'app/shared/model/type-cond.model';
import { TypeCondService } from './type-cond.service';
import { TypeCondComponent } from './type-cond.component';
import { TypeCondDetailComponent } from './type-cond-detail.component';
import { TypeCondUpdateComponent } from './type-cond-update.component';

@Injectable({ providedIn: 'root' })
export class TypeCondResolve implements Resolve<ITypeCond> {
  constructor(private service: TypeCondService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeCond> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeCond: HttpResponse<TypeCond>) => {
          if (typeCond.body) {
            return of(typeCond.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeCond());
  }
}

export const typeCondRoute: Routes = [
  {
    path: '',
    component: TypeCondComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeCond.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeCondDetailComponent,
    resolve: {
      typeCond: TypeCondResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeCond.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeCondUpdateComponent,
    resolve: {
      typeCond: TypeCondResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeCond.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeCondUpdateComponent,
    resolve: {
      typeCond: TypeCondResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeCond.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
