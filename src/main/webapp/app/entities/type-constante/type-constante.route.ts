import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeConstante, TypeConstante } from 'app/shared/model/type-constante.model';
import { TypeConstanteService } from './type-constante.service';
import { TypeConstanteComponent } from './type-constante.component';
import { TypeConstanteDetailComponent } from './type-constante-detail.component';
import { TypeConstanteUpdateComponent } from './type-constante-update.component';

@Injectable({ providedIn: 'root' })
export class TypeConstanteResolve implements Resolve<ITypeConstante> {
  constructor(private service: TypeConstanteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeConstante> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeConstante: HttpResponse<TypeConstante>) => {
          if (typeConstante.body) {
            return of(typeConstante.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeConstante());
  }
}

export const typeConstanteRoute: Routes = [
  {
    path: '',
    component: TypeConstanteComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeConstante.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeConstanteDetailComponent,
    resolve: {
      typeConstante: TypeConstanteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeConstante.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeConstanteUpdateComponent,
    resolve: {
      typeConstante: TypeConstanteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeConstante.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeConstanteUpdateComponent,
    resolve: {
      typeConstante: TypeConstanteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeConstante.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
