import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeImmo, TypeImmo } from 'app/shared/model/type-immo.model';
import { TypeImmoService } from './type-immo.service';
import { TypeImmoComponent } from './type-immo.component';
import { TypeImmoDetailComponent } from './type-immo-detail.component';
import { TypeImmoUpdateComponent } from './type-immo-update.component';

@Injectable({ providedIn: 'root' })
export class TypeImmoResolve implements Resolve<ITypeImmo> {
  constructor(private service: TypeImmoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeImmo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeImmo: HttpResponse<TypeImmo>) => {
          if (typeImmo.body) {
            return of(typeImmo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeImmo());
  }
}

export const typeImmoRoute: Routes = [
  {
    path: '',
    component: TypeImmoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeImmo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeImmoDetailComponent,
    resolve: {
      typeImmo: TypeImmoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeImmo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeImmoUpdateComponent,
    resolve: {
      typeImmo: TypeImmoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeImmo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeImmoUpdateComponent,
    resolve: {
      typeImmo: TypeImmoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeImmo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
