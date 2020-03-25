import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypePole, TypePole } from 'app/shared/model/type-pole.model';
import { TypePoleService } from './type-pole.service';
import { TypePoleComponent } from './type-pole.component';
import { TypePoleDetailComponent } from './type-pole-detail.component';
import { TypePoleUpdateComponent } from './type-pole-update.component';

@Injectable({ providedIn: 'root' })
export class TypePoleResolve implements Resolve<ITypePole> {
  constructor(private service: TypePoleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypePole> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typePole: HttpResponse<TypePole>) => {
          if (typePole.body) {
            return of(typePole.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypePole());
  }
}

export const typePoleRoute: Routes = [
  {
    path: '',
    component: TypePoleComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePole.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypePoleDetailComponent,
    resolve: {
      typePole: TypePoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePole.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypePoleUpdateComponent,
    resolve: {
      typePole: TypePoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePole.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypePoleUpdateComponent,
    resolve: {
      typePole: TypePoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePole.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
