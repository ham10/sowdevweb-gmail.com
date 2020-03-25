import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeMvtStock, TypeMvtStock } from 'app/shared/model/type-mvt-stock.model';
import { TypeMvtStockService } from './type-mvt-stock.service';
import { TypeMvtStockComponent } from './type-mvt-stock.component';
import { TypeMvtStockDetailComponent } from './type-mvt-stock-detail.component';
import { TypeMvtStockUpdateComponent } from './type-mvt-stock-update.component';

@Injectable({ providedIn: 'root' })
export class TypeMvtStockResolve implements Resolve<ITypeMvtStock> {
  constructor(private service: TypeMvtStockService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeMvtStock> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeMvtStock: HttpResponse<TypeMvtStock>) => {
          if (typeMvtStock.body) {
            return of(typeMvtStock.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeMvtStock());
  }
}

export const typeMvtStockRoute: Routes = [
  {
    path: '',
    component: TypeMvtStockComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeMvtStock.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeMvtStockDetailComponent,
    resolve: {
      typeMvtStock: TypeMvtStockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeMvtStock.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeMvtStockUpdateComponent,
    resolve: {
      typeMvtStock: TypeMvtStockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeMvtStock.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeMvtStockUpdateComponent,
    resolve: {
      typeMvtStock: TypeMvtStockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeMvtStock.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
