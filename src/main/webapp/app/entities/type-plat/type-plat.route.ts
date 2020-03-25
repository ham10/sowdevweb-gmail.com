import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypePlat, TypePlat } from 'app/shared/model/type-plat.model';
import { TypePlatService } from './type-plat.service';
import { TypePlatComponent } from './type-plat.component';
import { TypePlatDetailComponent } from './type-plat-detail.component';
import { TypePlatUpdateComponent } from './type-plat-update.component';

@Injectable({ providedIn: 'root' })
export class TypePlatResolve implements Resolve<ITypePlat> {
  constructor(private service: TypePlatService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypePlat> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typePlat: HttpResponse<TypePlat>) => {
          if (typePlat.body) {
            return of(typePlat.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypePlat());
  }
}

export const typePlatRoute: Routes = [
  {
    path: '',
    component: TypePlatComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePlat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypePlatDetailComponent,
    resolve: {
      typePlat: TypePlatResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePlat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypePlatUpdateComponent,
    resolve: {
      typePlat: TypePlatResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePlat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypePlatUpdateComponent,
    resolve: {
      typePlat: TypePlatResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePlat.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
