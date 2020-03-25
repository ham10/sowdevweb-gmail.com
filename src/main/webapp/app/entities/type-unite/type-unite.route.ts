import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeUnite, TypeUnite } from 'app/shared/model/type-unite.model';
import { TypeUniteService } from './type-unite.service';
import { TypeUniteComponent } from './type-unite.component';
import { TypeUniteDetailComponent } from './type-unite-detail.component';
import { TypeUniteUpdateComponent } from './type-unite-update.component';

@Injectable({ providedIn: 'root' })
export class TypeUniteResolve implements Resolve<ITypeUnite> {
  constructor(private service: TypeUniteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeUnite> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeUnite: HttpResponse<TypeUnite>) => {
          if (typeUnite.body) {
            return of(typeUnite.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeUnite());
  }
}

export const typeUniteRoute: Routes = [
  {
    path: '',
    component: TypeUniteComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeUnite.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeUniteDetailComponent,
    resolve: {
      typeUnite: TypeUniteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeUnite.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeUniteUpdateComponent,
    resolve: {
      typeUnite: TypeUniteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeUnite.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeUniteUpdateComponent,
    resolve: {
      typeUnite: TypeUniteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeUnite.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
