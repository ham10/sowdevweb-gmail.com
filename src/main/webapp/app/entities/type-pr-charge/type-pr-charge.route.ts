import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypePrCharge, TypePrCharge } from 'app/shared/model/type-pr-charge.model';
import { TypePrChargeService } from './type-pr-charge.service';
import { TypePrChargeComponent } from './type-pr-charge.component';
import { TypePrChargeDetailComponent } from './type-pr-charge-detail.component';
import { TypePrChargeUpdateComponent } from './type-pr-charge-update.component';

@Injectable({ providedIn: 'root' })
export class TypePrChargeResolve implements Resolve<ITypePrCharge> {
  constructor(private service: TypePrChargeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypePrCharge> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typePrCharge: HttpResponse<TypePrCharge>) => {
          if (typePrCharge.body) {
            return of(typePrCharge.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypePrCharge());
  }
}

export const typePrChargeRoute: Routes = [
  {
    path: '',
    component: TypePrChargeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePrCharge.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypePrChargeDetailComponent,
    resolve: {
      typePrCharge: TypePrChargeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePrCharge.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypePrChargeUpdateComponent,
    resolve: {
      typePrCharge: TypePrChargeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePrCharge.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypePrChargeUpdateComponent,
    resolve: {
      typePrCharge: TypePrChargeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePrCharge.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
