import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeCaisse, TypeCaisse } from 'app/shared/model/type-caisse.model';
import { TypeCaisseService } from './type-caisse.service';
import { TypeCaisseComponent } from './type-caisse.component';
import { TypeCaisseDetailComponent } from './type-caisse-detail.component';
import { TypeCaisseUpdateComponent } from './type-caisse-update.component';

@Injectable({ providedIn: 'root' })
export class TypeCaisseResolve implements Resolve<ITypeCaisse> {
  constructor(private service: TypeCaisseService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeCaisse> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeCaisse: HttpResponse<TypeCaisse>) => {
          if (typeCaisse.body) {
            return of(typeCaisse.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeCaisse());
  }
}

export const typeCaisseRoute: Routes = [
  {
    path: '',
    component: TypeCaisseComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeCaisse.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeCaisseDetailComponent,
    resolve: {
      typeCaisse: TypeCaisseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeCaisse.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeCaisseUpdateComponent,
    resolve: {
      typeCaisse: TypeCaisseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeCaisse.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeCaisseUpdateComponent,
    resolve: {
      typeCaisse: TypeCaisseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeCaisse.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
