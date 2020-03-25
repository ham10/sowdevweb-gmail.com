import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeLit, TypeLit } from 'app/shared/model/type-lit.model';
import { TypeLitService } from './type-lit.service';
import { TypeLitComponent } from './type-lit.component';
import { TypeLitDetailComponent } from './type-lit-detail.component';
import { TypeLitUpdateComponent } from './type-lit-update.component';

@Injectable({ providedIn: 'root' })
export class TypeLitResolve implements Resolve<ITypeLit> {
  constructor(private service: TypeLitService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeLit> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeLit: HttpResponse<TypeLit>) => {
          if (typeLit.body) {
            return of(typeLit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeLit());
  }
}

export const typeLitRoute: Routes = [
  {
    path: '',
    component: TypeLitComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeLit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeLitDetailComponent,
    resolve: {
      typeLit: TypeLitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeLit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeLitUpdateComponent,
    resolve: {
      typeLit: TypeLitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeLit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeLitUpdateComponent,
    resolve: {
      typeLit: TypeLitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeLit.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
