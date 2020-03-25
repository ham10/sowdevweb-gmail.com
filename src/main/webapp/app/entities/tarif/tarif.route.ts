import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITarif, Tarif } from 'app/shared/model/tarif.model';
import { TarifService } from './tarif.service';
import { TarifComponent } from './tarif.component';
import { TarifDetailComponent } from './tarif-detail.component';
import { TarifUpdateComponent } from './tarif-update.component';

@Injectable({ providedIn: 'root' })
export class TarifResolve implements Resolve<ITarif> {
  constructor(private service: TarifService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITarif> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tarif: HttpResponse<Tarif>) => {
          if (tarif.body) {
            return of(tarif.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Tarif());
  }
}

export const tarifRoute: Routes = [
  {
    path: '',
    component: TarifComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.tarif.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TarifDetailComponent,
    resolve: {
      tarif: TarifResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.tarif.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TarifUpdateComponent,
    resolve: {
      tarif: TarifResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.tarif.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TarifUpdateComponent,
    resolve: {
      tarif: TarifResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.tarif.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
